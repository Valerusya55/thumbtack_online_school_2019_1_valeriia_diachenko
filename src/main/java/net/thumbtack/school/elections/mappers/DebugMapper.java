package net.thumbtack.school.elections.mappers;

import org.apache.ibatis.annotations.Delete;

public interface DebugMapper {
    @Delete("DELETE FROM user WHERE id>0")
    void clearDataBase();
}
