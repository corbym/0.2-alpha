package org.groovymud.engine;

import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.groovymud.object.registry.MudObjectAttendant;

public class ScriptScanner {

    private final static Logger logger = Logger.getLogger(ScriptScanner.class);
    private MudObjectAttendant attendant;
    private String mudSpace;

    public void scan() {
        List<String> files = findClasses(new File(mudSpace.trim() + "/domains/"));
        for (String f : files) {
            try {
                logger.info("loading class " + f + "... ");
                attendant.loadClass(f.trim());
                logger.info(".. done.");
            } catch (ResourceException e) {
                logger.error(e, e);
            } catch (ScriptException e) {
                logger.error(e, e);
            }
        }
    }

    class FileFilterer implements FileFilter {

        public boolean accept(File pathname) {
            return pathname.isDirectory() || pathname.getName().endsWith(".groovy");
        }

    }

    public List<String> findClasses(File dir) {
        List<String> jspList = new ArrayList();
        File[] files = dir.listFiles(new FileFilterer());
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isDirectory()) {
                jspList.addAll(findClasses(file));
            } else {
                jspList.add(adjustPath(file.getPath()));
            }
        }
        return jspList;
    }

    protected String adjustPath(String path) {
        String mudSpace = this.mudSpace.trim();
        String realSpace = mudSpace.replace("/", File.separator);
        String adjustedPath = path.substring(path.lastIndexOf(realSpace) + realSpace.length() + 1, path.indexOf(".groovy"));
        return new String(adjustedPath);
    }

    public MudObjectAttendant getAttendant() {
        return attendant;
    }

    public void setAttendant(MudObjectAttendant attendant) {
        this.attendant = attendant;
    }

    public String getMudSpace() {
        return mudSpace;
    }

    public void setMudSpace(String mudSpace) {
        this.mudSpace = mudSpace;
    }
}
