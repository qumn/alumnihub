import io.github.qumn.ktorm.search.*
import io.github.qumn.test.DBIntegrationSpec
import org.ktorm.database.Database
import org.ktorm.entity.toList
import java.text.SimpleDateFormat
import java.time.LocalDate


class SearchTest(
    database: Database,
) : DBIntegrationSpec({

    data class DepartmentReq(
        val id: Int? = null,
        @Operation(RLIKE::class) val name: String? = null,
    )

    data class EmployeeReq(
        val id: Int? = null,
        @Operation(LIKE::class) val name: String? = null,
        @Operation(RLIKE::class) val job: String? = null,
        val managerId: Int? = null,
        @Operation(BETWEEN::class) val hireDate: Array<LocalDate?>? = null,
        @Operation(GT::class) val salary: Long? = null,
//        val departmentId: Int? = null,
        @Operation(columnName = "departmentId") val department: DepartmentReq? = null,
    )

    "like should work api search should work" {
        val employeeReq = EmployeeReq(name = "m")
        val departments = database.employees.search(employeeReq).toList()
        departments.forEach {
            assert(it.name.contains("m"))
        }
    }

    "rLike should work api search should work" {
        val employeeReq = EmployeeReq(job = "e")
        val departments = database.employees.search(employeeReq).toList()
        departments.forEach {
            println(it)
            assert(it.job.startsWith("e"))
        }
    }

    "between should work" {
        val begin = LocalDate.of(2017, 12, 30)
        val end = LocalDate.of(2018, 2, 1)
        val employeeReq = EmployeeReq(hireDate = arrayOf(begin, end))
        val departments = database.employees.search(employeeReq).toList()
        departments.forEach {
            println(it)
        }
    }

    "between only right end should work" {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val begin = LocalDate.of(2017, 12, 30)
        val end = LocalDate.of(2018, 2, 1)
        val employeeReq = EmployeeReq(hireDate = arrayOf(null, end))
        val departments = database.employees.search(employeeReq).toList()
        departments.forEach {
            println(it)
        }
    }

    "between only left end should work" {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val begin = LocalDate.of(2017, 12, 30)
        val end = LocalDate.of(2018, 2, 1)
        val employeeReq = EmployeeReq(hireDate = arrayOf(begin, null))
        val departments = database.employees.search(employeeReq).toList()
        departments.forEach {
            println(it)
        }
    }

    "nested should work".config(invocations = 10) {
        val departmentReq = DepartmentReq(name = "e")
        val employeeReq = EmployeeReq(department = departmentReq)
        val departments = database.employees.search(employeeReq).toList()
        departments.forEach {
            println(it)
        }
    }
})