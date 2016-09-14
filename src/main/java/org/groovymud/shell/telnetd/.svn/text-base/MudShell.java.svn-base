package org.groovymud.shell.telnetd;

import java.io.IOException;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import net.wimpi.telnetd.net.Connection;
import net.wimpi.telnetd.net.ConnectionEvent;
import net.wimpi.telnetd.shell.Shell;

import org.apache.log4j.Logger;
import org.groovymud.object.alive.Alive;
import org.groovymud.object.alive.Player;
import org.groovymud.shell.command.CommandInterpreter;
import org.groovymud.shell.security.PasswordService;

public class MudShell extends ShellBridge {

    private CommandInterpreter interpreter;
    protected static final String BEAN_NAME = "mudShell";
    private final static Logger logger = Logger.getLogger(MudShell.class);
    
    public void run(Connection connection) {
        setConnection(connection);
        setTerminalIO(createTerminalIO(connection));
        Player player = (Player) getConnection().getConnectionData().getEnvironment().get("player");
        LoginContext lc = (LoginContext) getConnection().getConnectionData().getEnvironment().get("loginContext");
        getInterpreter().doShellCommand("look", player);
        try {
            while (getObjectRegistry().getMudObject(player.getName()) != null) {
                handleRequest(player);
            }
        } catch (IOException e) {
            logger.error(e, e);
        } finally {
            try {
                lc.logout();
            } catch (LoginException e) {
                logger.error(e, e);
            } finally {
                connection.close();
            }
        }
    }

    public static Shell createShell() {
        return (Shell) getContext().getBean(BEAN_NAME);
    }

    private void handleRequest(Player player) throws IOException {
        ExtendedTerminalIO io = getTerminalIO();
        io.write("\r\n>");
        io.flush();
        String command;
        try {
            command = io.readln(false);
            getInterpreter().doShellCommand(command, player);
        } catch (Exception e) {
            logger.error(e, e);
            try {
                player.getTerminalOutput().writeln("You cannot do that.");
            } catch (IOException e1) {
                logger.error(e1, e1);
            }

        }
    }

    public void connectionIdle(ConnectionEvent ce) {
        try {
            getTerminalIO().write("CONNECTION_IDLE");

            getTerminalIO().flush();
        } catch (IOException e) {
            logger.error(e, e);
        }
    }// connectionIdle

    // this implements the ConnectionListener!
    public void connectionTimedOut(ConnectionEvent ce) {
        try {
            getTerminalIO().write("CONNECTION_TIMEDOUT");
            getTerminalIO().flush();
            // close connection
            getConnection().close();
        } catch (Exception ex) {
            logger.error("connectionTimedOut()", ex);
        }
    }// connectionTimedOut

    public void connectionLogoutRequest(ConnectionEvent ce) {
        try {
            getTerminalIO().write("CONNECTION_LOGOUTREQUEST");
            getTerminalIO().flush();
        } catch (IOException e) {
            logger.error(e, e);
        }

    }// connectionLogout

    public void connectionSentBreak(ConnectionEvent ce) {
        try {
            getTerminalIO().write("CONNECTION_BREAK");
            getTerminalIO().flush();
        } catch (IOException e) {
            logger.error(e, e);
        }

    }

    public CommandInterpreter getInterpreter() {
        return interpreter;
    }

    public void setInterpreter(CommandInterpreter interpreter) {
        this.interpreter = interpreter;
    }
  
}
