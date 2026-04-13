// Updated ClientService.java to fix doc number inconsistency
// Example lines 27, 38, 41, and 64 are shown below:

public class ClientService {
    // other methods...
    public void someMethod() {
        // line 27
        String docNumber = client.getDocnumber();

        // line 38
        client.setDocnumber(newDocNumber);

        // line 41
        String docNumberValue = client.getDocnumber();

        // line 64
        if (someCondition) {
            client.setDocnumber(someValue);
        }
    }
}