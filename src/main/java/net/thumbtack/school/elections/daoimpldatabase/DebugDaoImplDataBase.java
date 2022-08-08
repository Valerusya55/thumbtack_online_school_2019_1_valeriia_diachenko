package net.thumbtack.school.elections.daoimpldatabase;

import net.thumbtack.school.elections.dao.DebugDao;
import net.thumbtack.school.elections.database.DataBase;

public class DebugDaoImplDataBase implements DebugDao {
    @Override
    public void clearDataBase() {
        DataBase.getDataBase().clearDataBase();
    }
}
