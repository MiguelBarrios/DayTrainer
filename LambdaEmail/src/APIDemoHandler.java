import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

public class APIDemoHandler implements RequestStreamHandler {

    private static final String DYNAMODB_TABLE_NAME = System.getenv("TABLE_NAME"); 
    
    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

        // implementation
      }

    public void handleGetByParam(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

        // implementation
    }
}