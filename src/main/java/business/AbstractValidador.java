package business;

public abstract class AbstractValidador implements IStrategy{
    protected StringBuilder sb = new StringBuilder();

    protected boolean isNull(Object obj){
        return obj == null || obj.toString().trim().isEmpty();
    }
}