package org.switchyard.quickstarts.camel.sql.binding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.switchyard.component.bean.Reference;
import org.switchyard.component.bean.Service;

@Service(GreetingService.class)
public class GreetingServiceImpl implements GreetingService {

    @Inject @Reference
    private StoreService storeService;

    @Override
    public void store(Greeting greeting) {
        storeService.execute(new SqlOperation(null, greeting));
    }

    @Override
    public List<Greeting> retrieve() {
        List<Map<String, Object>> rows = storeService.execute(new SqlOperation("SELECT * FROM greetings", null));
        List<Greeting> greetings = new ArrayList<Greeting>();
        for (Map<String, Object> greeting : rows) {
            greetings.add(new Greeting(
                (String) greeting.get("NAME"),
                (String) greeting.get("SENDER")
            ));
        }
        return greetings;
    }

}
