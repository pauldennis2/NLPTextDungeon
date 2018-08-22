package paul.NLPTextDungeon.parsing;

import java.util.List;

/**
 * Created by pauldennis on 8/20/17.
 */
public abstract class UserInterfaceClass {

    protected List<UserInterfaceClass> children;
    protected UserInterfaceClass requester;
    protected UserInterfaceClass defaultRequester;
    //Using a default requester means the class forgoes the option to handle inputs itself

    protected TextInterface textOut;

    public abstract void start (TextInterface textOut);

    public abstract InputType show ();

    public final InputType processResponse (String response) {
        if (defaultRequester != null && requester == null) {
            requester = defaultRequester;
        }
        if (requester != null) {
            return requester.processResponse(response);
        } else {
            return handleResponse(response);
        }
    }

    protected InputType handleResponse (String response) {
        throw new AssertionError("This class doesn't handle responses. Method called in error");
    }
}
