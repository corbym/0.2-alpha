package org.groovymud.shell.telnetd;

/* Copyright 2008 Matthew Corby-Eaglen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.wimpi.telnetd.io.BasicTerminalIO;
import net.wimpi.telnetd.net.Connection;

import org.apache.log4j.Logger;

/**
 * 
 * Wrapper for BasicTerminalIO that allows the user to enter text into a
 * stringbuffer using the telnetd code. Allows deleting multiple characters and
 * gives the user a buffer to play with too
 */
public class ExtendedTerminalIO implements BasicTerminalIO {

    private final static Logger logger = Logger.getLogger(ExtendedTerminalIO.class);

    private BasicTerminalIO basicTerminalIO;

    private List previousCommands;

    public ExtendedTerminalIO(Connection connection) {
        if (connection != null) {
            setBasicTerminalIO(connection.getTerminalIO());
        }
        setPreviousCommands(new ArrayList());
    }

    public String readln(boolean quiet) throws IOException {
        int i = 0;
        String str = "";
        int index = getPreviousCommands().size() - 1;

        while ((i = read()) != BasicTerminalIO.ENTER && i != -1) {
            if (i == BACKSPACE) {
                str = deleteCharacters(str, 1);
                continue;
            }
            if (i == UP || i == DOWN) {
                String prev = (String) getPreviousCommands().get(index == 0 ? index : i == UP ? index-- : index++);
                str = replace(str, prev);
                continue;
            }
            str = str + ((char) i);

        }
        getPreviousCommands().add(str);

        return str;
    }

    private String replace(String str, String prev) throws IOException {
        if (str != null) {
            str = prev;
            deleteCharacters(str, str.length());
        }
        return str;
    }

    protected String deleteCharacters(String str, int number) throws IOException {
        while (str.length() > 0 && number > 0) {
            str = str.substring(0, str.length() - 1);
            number--;
        }
        return str;
    }

    public void writeln(String string) throws IOException {
        write(string + BasicTerminalIO.CRLF);
    }

    public void bell() throws IOException {
        getBasicTerminalIO().bell();
    }

    public void close() throws IOException {
        getBasicTerminalIO().close();
    }

    public boolean defineScrollRegion(int arg0, int arg1) throws IOException {
        return getBasicTerminalIO().defineScrollRegion(arg0, arg1);

    }

    public void eraseLine() throws IOException {
        getBasicTerminalIO().eraseLine();

    }

    public void eraseScreen() throws IOException {
        getBasicTerminalIO().eraseScreen();

    }

    public void eraseToBeginOfLine() throws IOException {
        getBasicTerminalIO().eraseToBeginOfLine();

    }

    public void eraseToBeginOfScreen() throws IOException {
        getBasicTerminalIO().eraseToBeginOfScreen();

    }

    public void eraseToEndOfLine() throws IOException {
        getBasicTerminalIO().eraseToEndOfLine();

    }

    public void eraseToEndOfScreen() throws IOException {
        getBasicTerminalIO().eraseToEndOfScreen();

    }

    public void flush() throws IOException {
        getBasicTerminalIO().flush();

    }

    public void forceBold(boolean arg0) {
        getBasicTerminalIO().forceBold(arg0);
    }

    public int getColumns() {
        return getBasicTerminalIO().getColumns();
    }

    public int getRows() {
        return getBasicTerminalIO().getRows();
    }

    public void homeCursor() throws IOException {
        getBasicTerminalIO().homeCursor();

    }

    public boolean isAutoflushing() {
        return getBasicTerminalIO().isAutoflushing();
    }

    public boolean isLineWrapping() throws IOException {
        return getBasicTerminalIO().isLineWrapping();

    }

    public boolean isSignalling() {
        return getBasicTerminalIO().isSignalling();
    }

    public void moveCursor(int arg0, int arg1) throws IOException {
        getBasicTerminalIO().moveCursor(arg0, arg1);

    }

    public void moveDown(int arg0) throws IOException {
        getBasicTerminalIO().moveDown(arg0);

    }

    public void moveLeft(int arg0) throws IOException {
        getBasicTerminalIO().moveLeft(arg0);

    }

    public void moveRight(int arg0) throws IOException {
        getBasicTerminalIO().moveRight(arg0);

    }

    public void moveUp(int arg0) throws IOException {
        getBasicTerminalIO().moveUp(arg0);

    }

    public int read() throws IOException {
        return getBasicTerminalIO().read();

    }

    public void resetAttributes() throws IOException {
        getBasicTerminalIO().resetAttributes();

    }

    public void resetTerminal() throws IOException {
        getBasicTerminalIO().resetTerminal();

    }

    public void restoreCursor() throws IOException {
        getBasicTerminalIO().restoreCursor();

    }

    public void setAutoflushing(boolean arg0) {
        getBasicTerminalIO().setAutoflushing(arg0);
    }

    public void setBackgroundColor(int arg0) throws IOException {
        getBasicTerminalIO().setBackgroundColor(arg0);

    }

    public void setBlink(boolean arg0) throws IOException {
        getBasicTerminalIO().setBlink(arg0);

    }

    public void setBold(boolean arg0) throws IOException {
        getBasicTerminalIO().setBold(arg0);

    }

    public void setCursor(int arg0, int arg1) throws IOException {
        getBasicTerminalIO().setCursor(arg0, arg1);

    }

    public void setDefaultTerminal() throws IOException {
        getBasicTerminalIO().setDefaultTerminal();

    }

    public void setForegroundColor(int arg0) throws IOException {
        getBasicTerminalIO().setForegroundColor(arg0);

    }

    public void setItalic(boolean arg0) throws IOException {
        getBasicTerminalIO().setItalic(arg0);

    }

    public void setLinewrapping(boolean arg0) throws IOException {
        getBasicTerminalIO().setLinewrapping(arg0);

    }

    public void setSignalling(boolean arg0) {
        getBasicTerminalIO().setSignalling(arg0);
    }

    public void setTerminal(String arg0) throws IOException {
        getBasicTerminalIO().setTerminal(arg0);

    }

    public void setUnderlined(boolean arg0) throws IOException {
        getBasicTerminalIO().setUnderlined(arg0);

    }

    public void storeCursor() throws IOException {
        getBasicTerminalIO().storeCursor();

    }

    public void write(byte arg0) throws IOException {
        getBasicTerminalIO().write(arg0);

    }

    public void write(char arg0) throws IOException {
        getBasicTerminalIO().write(arg0);
    }

    public void write(String arg0) throws IOException {
        getBasicTerminalIO().write(arg0);

    }

    void setPreviousCommands(List previousCommands) {
        this.previousCommands = previousCommands;
    }

    List getPreviousCommands() {
        return previousCommands;
    }

    protected void setBasicTerminalIO(BasicTerminalIO basicTerminalIO) {
        this.basicTerminalIO = basicTerminalIO;
    }

    protected BasicTerminalIO getBasicTerminalIO() {
        return basicTerminalIO;
    }

}
