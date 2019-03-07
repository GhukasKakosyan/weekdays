package net.bsoftlab.dao;

import net.bsoftlab.dao.exception.DataAccessException;
import net.bsoftlab.model.Workday;

import java.util.List;

public interface WorkdayDao {
    void insertWorkdays(List<Workday> workdayList) throws DataAccessException;
}
