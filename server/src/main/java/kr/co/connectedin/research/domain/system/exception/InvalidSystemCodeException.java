package kr.ac.yonsei.maist.domain.system.exception;

public class InvalidSystemCodeException extends Exception {
    public InvalidSystemCodeException(String systemCode) {super("Invalid System code:" + systemCode);}
}
