-- Table Weekdays.Entries
CREATE TABLE Daysholder.Workdays (
  id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
  Weekday INTEGER NOT NULL,
  Day INTEGER NOT NULL,
  Month INTEGER NOT NULL,
  Year INTEGER NOT NULL,
  UNIQUE KEY UK_Entries (Day, Month, Year)

) ENGINE = InnoDB AUTO_INCREMENT = 1 DEFAULT CHARSET = utf8;