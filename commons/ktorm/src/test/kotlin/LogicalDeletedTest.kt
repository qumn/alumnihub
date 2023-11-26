import org.junit.jupiter.api.Test
import org.ktorm.dsl.from
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.entity.toList

class LogicalDeletedTest : BaseTest() {
    @Test
    fun logicalDeleteSequenceApiShouldWork() {
        val departments = database.departments.toList()
        val firstDept = departments[0]
        firstDept.delete()
        assert( database.departments.toList().size + 1 == departments.size)
    }

    @Test
    fun logicalDeleteDSLShouldWork() {
        val departments = database.from(Departments).select().map { Departments.createEntity(it) }
        val firstDept = departments[0]
        firstDept.delete()
        val departmentsAfterDeleted = database.from(Departments).select().map { Departments.createEntity(it) }
        assert(departmentsAfterDeleted.size +1 == departments.size)
    }

}