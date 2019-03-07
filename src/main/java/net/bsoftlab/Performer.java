package net.bsoftlab;

import net.bsoftlab.dao.EntryDao;
import net.bsoftlab.dao.WorkdayDao;
import net.bsoftlab.dao.datasource.DataSourceFactory;
import net.bsoftlab.dao.exception.DataAccessException;
import net.bsoftlab.dao.implement.EntryDaoImpl;
import net.bsoftlab.dao.implement.WorkdayDaoImpl;
import net.bsoftlab.model.Entry;
import net.bsoftlab.model.Workday;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

public class Performer {
    public static void main(String[] arguments) {
        if (arguments == null || arguments.length != 2) {
            System.out.println("You have entered incorrect quantity of arguments.");
            System.out.println("You must enter two arguments in following pattern:");
            System.out.println("startDate=yyyy-MM-dd endDate=yyyy-MM-dd");
            return;
        }

        if (!arguments[0].contains("=")) {
            System.out.println("You have entered incorrect first argument.");
            System.out.println("First argument must be in pattern: startDate=yyyy-MM-dd");
            return;
        }
        String[] startDateArgument = arguments[0].split("[=]");
        if (startDateArgument.length != 2 ||
                startDateArgument[0] == null || startDateArgument[0].isEmpty() ||
                startDateArgument[1] == null || startDateArgument[1].isEmpty()) {
            System.out.println("You have entered incorrect first argument.");
            System.out.println("First argument must be in pattern: startDate=yyyy-MM-dd");
            return;
        }
        if (!startDateArgument[0].equals("startDate")) {
            System.out.println("You have entered incorrect name of first argument.");
            System.out.println("First argument name must be startDate");
            return;
        }

        if (!arguments[1].contains("=")) {
            System.out.println("You have entered incorrect second argument.");
            System.out.println("Second argument must be in pattern: endDate=yyyy-MM-dd");
            return;
        }
        String[] endDateArgument = arguments[1].split("[=]");
        if (endDateArgument.length != 2 ||
                endDateArgument[0] == null || endDateArgument[0].isEmpty() ||
                endDateArgument[1] == null || endDateArgument[1].isEmpty()) {
            System.out.println("You have entered incorrect second argument.");
            System.out.println("Second argument must be in pattern: endDate=yyyy-MM-dd");
            return;
        }
        if (!endDateArgument[0].equals("endDate")) {
            System.out.println("You have entered incorrect name of second argument.");
            System.out.println("Second argument name must be endDate");
            return;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date startDate;
        try {
            startDate = simpleDateFormat.parse(startDateArgument[1]);
        } catch (ParseException parseException) {
            System.out.println("You have entered incorrect value of first argument.");
            return;
        }
        Date endDate;
        try {
            endDate = simpleDateFormat.parse(endDateArgument[1]);
        } catch (ParseException parseException) {
            System.out.println("You have entered incorrect value of second argument.");
            return;
        }


        Calendar calendar = Calendar.getInstance();
        Date currentDate = startDate;
        List<Entry> entryList = new ArrayList<>();
        List<Workday> workdayList = new ArrayList<>();
        while (currentDate.before(endDate)) {
            calendar.setTime(currentDate);
            Workday workday = new Workday();
            workday.setWeekday(calendar.get(Calendar.DAY_OF_WEEK));
            workday.setDay(calendar.get(Calendar.DAY_OF_MONTH));
            workday.setMonth(calendar.get(Calendar.MONTH) + 1);
            workday.setYear(calendar.get(Calendar.YEAR));
            workdayList.add(workday);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            currentDate = calendar.getTime();
        }
        try {
            DataSource dataSource = DataSourceFactory.getDataSource();
            WorkdayDao workdayDao = new WorkdayDaoImpl(dataSource);
            workdayDao.insertWorkdays(workdayList);
            EntryDao entryDao = new EntryDaoImpl(dataSource);
            entryList = entryDao.getEntryList();
        } catch (DataAccessException dataAccessException) {
            System.out.println(dataAccessException.getMessage());
        }
        entryList.forEach(System.out::println);
    }
}
