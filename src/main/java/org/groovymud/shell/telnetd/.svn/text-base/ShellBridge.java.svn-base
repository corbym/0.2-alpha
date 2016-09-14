package org.groovymud.shell.telnetd;

import java.io.IOException;

import net.wimpi.telnetd.net.Connection;
import net.wimpi.telnetd.net.ConnectionEvent;
import net.wimpi.telnetd.shell.Shell;

import org.apache.log4j.Logger;
import org.groovymud.object.registry.Registry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * horrible fudge to make spring and telnetd more compatible function here
 */
public abstract class ShellBridge implements Shell, ApplicationContextAware {

    private final static Logger logger = Logger.getLogger(ShellBridge.class);

    private static ApplicationContext context;
    private ExtendedTerminalIO terminalIO;
    private Connection connection;
    private Registry objectRegistry;

    public void setApplicationContext(ApplicationContext context) {
        this.context = context;
    }

    public static ApplicationContext getContext() {
        return context;
    }

    protected ExtendedTerminalIO createTerminalIO(Connection connection) {
        return new ExtendedTerminalIO(connection);
    }

    public static void setShell(LoginShell shell) {
        throw new UnsupportedOperationException();
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

    public void run(Connection con) {
        throw new UnsupportedOperationException();
    }

    public static void setContext(ApplicationContext context) {
        ShellBridge.context = context;
    }

    public ExtendedTerminalIO getTerminalIO() {
        return terminalIO;
    }

    public void setTerminalIO(ExtendedTerminalIO terminalIO) {
        this.terminalIO = terminalIO;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setObjectRegistry(Registry objectRegistry) {
        this.objectRegistry = objectRegistry;
    }

    public Registry getObjectRegistry() {
        return objectRegistry;
    }

}
