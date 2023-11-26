import io.github.qumn.ktorm.base.BaseEntity
import io.github.qumn.ktorm.base.BaseTable
import io.github.qumn.ktorm.base.database
import io.github.qumn.ktorm.base.lazyFetch
import io.github.qumn.ktorm.dialet.KtAdmPostgreSqlDialect
import io.github.qumn.ktorm.interceptor.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.ktorm.database.Database
import org.ktorm.database.use
import org.ktorm.dsl.eq
import org.ktorm.entity.Entity
import org.ktorm.entity.filter
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList
import org.ktorm.logging.ConsoleLogger
import org.ktorm.logging.LogLevel
import org.ktorm.schema.date
import org.ktorm.schema.int
import org.ktorm.schema.long
import org.ktorm.schema.varchar
import java.io.Serializable
import java.time.LocalDate


val Database.departments get() = this.sequenceOf(BaseTest.Departments)

val Database.employees get() = this.sequenceOf(BaseTest.Employees)

val Database.customers get() = this.sequenceOf(BaseTest.Customers)

/**
 * Created by vince on Dec 07, 2018.
 */
abstract class BaseTest {
    lateinit var database: Database

    @BeforeEach
    open fun init() {
        val interceptor = CompositorVisitorInterceptor()
        interceptor.register(LogicalSelectVisitorInterceptor())
            .register(UpdateAutoFillVisitorInterceptor())
            .register(InsertAutoFillVisitorInterceptor())
            .register(LogicalDeleteInterceptor())
        val dialect = KtAdmPostgreSqlDialect(interceptor)
        database =
            Database.connect(
                "jdbc:postgresql://49.235.108.166:5432/qumn",
                user = "qumn",
                password = "qumn",
                logger = ConsoleLogger(threshold = LogLevel.DEBUG),
                alwaysQuoteIdentifiers = true, dialect = dialect
            )
        io.github.qumn.ktorm.base.database = database
        execSqlScript("init-data.sql")
    }

    @AfterEach
    open fun destroy() {
        execSqlScript("drop-data.sql")
    }

    protected fun execSqlScript(filename: String) {
        database.useConnection { conn ->
            conn.createStatement().use { statement ->
                javaClass.classLoader
                    ?.getResourceAsStream(filename)
                    ?.bufferedReader()
                    ?.use { reader ->
                        for (sql in reader.readText().split(';')) {
                            if (sql.any { it.isLetterOrDigit() }) {
                                statement.executeUpdate(sql)
                            }
                        }
                    }
            }
        }
    }

    data class LocationWrapper(val underlying: String = "") : Serializable

    interface Department : BaseEntity<Department> {
        companion object : Entity.Factory<Department>()

        val id: Int
        val pid: Int
        var name: String
        var location: LocationWrapper
        var mixedCase: String?

        val employees
            get() = this.lazyFetch("employees") {
                database.employees.filter { it.departmentId eq this.id }.toList()
            }

        val children
            get() = this.lazyFetch("children") {
                database.departments.filter { it.pid eq this.id }.toList()
            }


        fun isContain(id: Int) =
            this.children.any { it.id == id }
    }

    interface Employee : BaseEntity<Employee> {
        companion object : Entity.Factory<Employee>()

        var id: Int
        var name: String
        var job: String
        var manager: Employee?
        var hireDate: LocalDate
        var salary: Long
        var department: Department
        val upperName get() = name.uppercase()
        fun upperName() = name.uppercase()
        fun nameWithPrefix(prefix: String) = prefix + name
        fun nameWithSuffix(suffix: String) = name + suffix
    }

    interface Customer : BaseEntity<Customer> {
        companion object : Entity.Factory<Customer>()

        var id: Int
        var name: String
        var email: String
        var phoneNumber: String
    }

    open class Departments(alias: String?) : BaseTable<Department>("t_department", alias) {
        companion object : Departments(null)

        override fun aliased(alias: String) = Departments(alias)

        val id = int("id").primaryKey().bindTo { it.id }
        val pid = int("pid").bindTo { it.pid }
        val name = varchar("name").bindTo { it.name }
        val location = varchar("location").transform({ LocationWrapper(it) }, { it.underlying }).bindTo { it.location }
        val mixedCase = varchar("mixedCase").bindTo { it.mixedCase }
    }

    open class Employees(alias: String?) : BaseTable<Employee>("t_employee", alias) {
        companion object : Employees(null)

        override fun aliased(alias: String) = Employees(alias)

        val id = int("id").primaryKey().bindTo { it.id }
        val name = varchar("name").bindTo { it.name }
        val job = varchar("job").bindTo { it.job }
        val managerId = int("manager_id").bindTo { it.manager?.id }
        val hireDate = date("hire_date").bindTo { it.hireDate }
        val salary = long("salary").bindTo { it.salary }
        val departmentId = int("department_id").references(Departments) { it.department }
        val department = departmentId.referenceTable as Departments
    }

    open class Customers(alias: String?) : BaseTable<Customer>("t_customer", alias, schema = "company") {
        companion object : Customers(null)

        override fun aliased(alias: String) = Customers(alias)

        val id = int("id").primaryKey().bindTo { it.id }
        val name = varchar("name").bindTo { it.name }
        val email = varchar("email").bindTo { it.email }
        val phoneNumber = varchar("phone_number").bindTo { it.phoneNumber }
    }

}
