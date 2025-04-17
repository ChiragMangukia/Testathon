package io.github.chiragmangukia.utils;

import com.google.common.io.Files;
import io.github.chiragmangukia.base.Base;
import org.openqa.selenium.OutputType;

import java.util.Calendar;
import java.util.Date;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

public class Utilities extends Base {
    public String takeScreenshot() {
        String fileName = "";
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            fileName = System.currentTimeMillis() + ".png";
            String path = "./Screenshots/" + fileName;
            File destination = new File(path);
            Files.copy(src, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public static Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}
