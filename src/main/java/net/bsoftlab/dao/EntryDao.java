package net.bsoftlab.dao;

import net.bsoftlab.dao.exception.DataAccessException;
import net.bsoftlab.model.Entry;

import java.util.List;

public interface EntryDao {
    List<Entry> getEntryList() throws DataAccessException;
}
