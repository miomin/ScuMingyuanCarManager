package scu.mingyuan.com.carmanager.httpparse;

/**
 * Created by 莫绪旻 on 16/3/15.
 */
public abstract class XmlRequestListener<T> extends MioAbstractParseListener<T> {

    @Override
    protected T bindData(String result) {
        return null;
    }
}