package net.thumbtack.school.elections.service;

import com.google.gson.Gson;
import net.thumbtack.school.elections.daoimpldatabase.DebugDaoImplDataBase;
import net.thumbtack.school.elections.dao.DebugDao;
import net.thumbtack.school.elections.daoimplmybatis.DebugDaoImplMyBatis;
import net.thumbtack.school.elections.settings.Mode;

public class DebugService {
    private DebugDao debugDao;
    private static Gson gson;

    public DebugService(Mode mode) {
        if (mode.equals(Mode.SQL)) {
            debugDao = new DebugDaoImplMyBatis();
        } else {
            debugDao = new DebugDaoImplDataBase();
        }
        gson = new Gson();
    }

    public void clearDataBase() {
        debugDao.clearDataBase();
    }
}
