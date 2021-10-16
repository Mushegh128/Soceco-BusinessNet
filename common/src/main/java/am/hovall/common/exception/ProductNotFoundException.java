package am.hovall.common.exception;

public class ProductNotFoundException extends RuntimeException{

    @Override
    public String getMessage() {
        return "Product Not Found";
    }
}
