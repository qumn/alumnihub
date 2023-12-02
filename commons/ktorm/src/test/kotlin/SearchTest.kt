import io.github.qumn.ktorm.search.*
import io.github.qumn.test.DBIntegrationSpec
import org.ktorm.database.Database
import org.ktorm.entity.single
import org.ktorm.entity.take
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

    "likeShouldWorkApiSearchShouldWork" {
        val employeeReq = EmployeeReq(name = "m")
        val departments = database.employees.search(employeeReq).toList()
        departments.forEach {
            assert(it.name.contains("m"))
        }
    }

    "rLikeShouldWorkApiSearchShouldWork" {
        val employeeReq = EmployeeReq(job = "e")
        val departments = database.employees.search(employeeReq).toList()
        departments.forEach {
            println(it)
            assert(it.job.startsWith("e"))
        }
    }

    "betweenShouldWork" {
        val begin = LocalDate.of(2017, 12, 30)
        val end = LocalDate.of(2018, 2, 1)
        val employeeReq = EmployeeReq(hireDate = arrayOf(begin, end))
        val departments = database.employees.search(employeeReq).toList()
        departments.forEach {
            println(it)
        }
    }

    "betweenOnlyRightEndShouldWork" {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val begin = LocalDate.of(2017, 12, 30)
        val end = LocalDate.of(2018, 2, 1)
        val employeeReq = EmployeeReq(hireDate = arrayOf(null, end))
        val departments = database.employees.search(employeeReq).toList()
        departments.forEach {
            println(it)
        }
    }

    "betweenOnlyLeftEndShouldWork" {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val begin = LocalDate.of(2017, 12, 30)
        val end = LocalDate.of(2018, 2, 1)
        val employeeReq = EmployeeReq(hireDate = arrayOf(begin, null))
        val departments = database.employees.search(employeeReq).toList()
        departments.forEach {
            println(it)
        }
    }

    "nestedShouldWork".config(invocations = 10) {
        val departmentReq = DepartmentReq(name = "e")
        val employeeReq = EmployeeReq(department = departmentReq)
        val departments = database.employees.search(employeeReq).toList()
        departments.forEach {
            println(it)
        }
    }

    "testOneToMany" {
        val department = database.departments.take(1).single()
        val db = database

        val employees = department.employees
        println(employees)
    }

})