import io.github.qumn.test.DBIntegrationSpec
import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.entity.toList

class LogicalDeletedTest(
    database: Database,
) : DBIntegrationSpec({
    "logical delete sequence api should work" {
        val departments = database.departments.toList()
        val firstDept = departments[0]
        firstDept.delete()
        assert(database.departments.toList().size + 1 == departments.size)
    }

    "logical delete DSL should work" {
        val departments = database.from(BaseTest.Departments).select().map { BaseTest.Departments.createEntity(it) }
        val firstDept = departments[0]
        firstDept.delete()
        val departmentsAfterDeleted =
            database.from(BaseTest.Departments).select().map { BaseTest.Departments.createEntity(it) }
        assert(departmentsAfterDeleted.size + 1 == departments.size)
    }
})