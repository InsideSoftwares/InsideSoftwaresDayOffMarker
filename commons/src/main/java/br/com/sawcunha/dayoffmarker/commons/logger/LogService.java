package br.com.sawcunha.dayoffmarker.commons.logger;

import br.com.sawcunha.dayoffmarker.commons.utils.LocaleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LogService<T> {

    private Logger logger;
    private String nameClass;
    private final Logger loggerService = LoggerFactory.getLogger(LogService.class);
    private static final String FORMAT_MESSAGE = "\n\tcode: %s\n\tmessage: %s\n\t%s";
    private static final String FORMAT_MESSAGE_NAME_CLASS = "Class: %s\n\tcode: %s\n\tmessage: %s\n\t%s";

    @Autowired
    private LocaleUtils localeUtils;

    public void init(Class<T> valueClass){
        this.loggerService.info(String.format("Init LogService: %s",valueClass.getName()));
        this.logger = LoggerFactory.getLogger(valueClass);
    }

    public void init(Class<T> valueClass, String nameClass){
        this.nameClass = nameClass;
        this.loggerService.info(String.format("Init LogService: %s",nameClass));
        this.logger = LoggerFactory.getLogger(valueClass);
    }

	public void logInfor(String message){
		logger.info(message);
	}

    public void logDebug(String message){
        logger.debug(message);
    }

    public void logWarn(String message){
        logger.warn(message);
    }

    public void logError(String message){
        logger.error(message);
    }

    public void logTrace(String message){
        logger.trace(message);
    }

    public void logInfor(String message, Throwable throwable){
        logger.info(message, throwable);
    }

    public void logDebug(String message, Throwable throwable){
        logger.debug(message, throwable);
    }

    public void logWarn(String message, Throwable throwable){
        logger.warn(message, throwable);
    }

    public void logError(String message, Throwable throwable){
        logger.error(message, throwable);
    }

    public void logTrace(String message, Throwable throwable){
        logger.trace(message, throwable);
    }

    public void logInfor(String code, String message, Throwable throwable){
        logger.info(createMessage(code, message), throwable);
    }

    public void logDebug(String code, String message, Throwable throwable){
        logger.debug(createMessage(code, message), throwable);
    }

    public void logWarn(String code, String message, Throwable throwable){
        logger.warn(createMessage(code, message), throwable);
    }

    public void logError(String code, String message, Throwable throwable){
        logger.error(createMessage(code, message), throwable);
    }

    public void logTrace(String code, String message, Throwable throwable){
        logger.trace(createMessage(code, message), throwable);
    }

    public void logInfor(String code, String message){
        logger.info(createMessage(code, message));
    }

    public void logDebug(String code, String message){
        logger.debug(createMessage(code, message));
    }

    public void logWarn(String code, String message){
        logger.warn(createMessage(code, message));
    }

    public void logError(String code, String message){
        logger.error(createMessage(code, message));
    }

    public void logTrace(String code, String message){
        logger.trace(createMessage(code, message));
    }

    public void logInfor(String code, String message, Throwable throwable, String ...args){
        logger.info(createMessage(code, message, args), throwable);
    }

    public void logDebug(String code, String message, Throwable throwable, String ...args){
        logger.debug(createMessage(code, message, args), throwable);
    }

    public void logWarn(String code, String message, Throwable throwable, String ...args){
        logger.warn(createMessage(code, message, args), throwable);
    }

    public void logError(String code, String message, Throwable throwable, String ...args){
        logger.error(createMessage(code, message, args), throwable);
    }

    public void logTrace(String code, String message, Throwable throwable, String ...args){
        logger.trace(createMessage(code, message, args), throwable);
    }

    public void logInfor(String code, String message, String ...args){
        logger.info(createMessage(code, message, args));
    }

    public void logDebug(String code, String message, String ...args){
        logger.debug(createMessage(code, message, args));
    }

    public void logWarn(String code, String message, String ...args){
        logger.warn(createMessage(code, message, args));
    }

    public void logError(String code, String message, String ...args){
        logger.error(createMessage(code, message, args));
    }

    public void logTrace(String code, String message, String ...args){
        logger.trace(createMessage(code, message, args));
    }

	public void logInforByArgs(String message, String... args){
		logger.info(message, args);
	}

	public void logInforByArgs(String message, Throwable throwable, String... args){
		logger.info(message, args, throwable);
	}

	public void logErrorByArgs(String message, String ...args){
		logger.error(message, args);
	}

    private String createMessage(String code, String message, String... args){
        String messageCode = localeUtils.getMessage(code, args);

        return Objects.isNull(nameClass) ?
                String.format(FORMAT_MESSAGE,code,messageCode,message) :
                String.format(FORMAT_MESSAGE_NAME_CLASS,nameClass,code,messageCode,message);

    }

}
