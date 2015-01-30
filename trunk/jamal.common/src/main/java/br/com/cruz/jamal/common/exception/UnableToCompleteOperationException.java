package br.com.cruz.jamal.common.exception;

public class UnableToCompleteOperationException extends JamalException {

	private static final long serialVersionUID = 5307457708834943674L;

	public UnableToCompleteOperationException(String operationName, String cause) {
		super();
		// TODO: FAZER A LÓGICA
	}
	
	public UnableToCompleteOperationException(String operationName, Throwable cause) {
		super(operationName, cause);
		// TODO: FAZER A LÓGICA
	}
}