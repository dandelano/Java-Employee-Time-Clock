package timeclock.utilities;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.SwingUtilities;

/**
 * Convenience class to request focus on a component.
 *
 * When the component is added to a realized Window then component will request
 * focus immediately, since the ancestorAdded event is fired immediately.
 *
 * When the component is added to a non realized Window, then the focus request
 * will be made once the window is realized, since the ancestorAdded event will
 * not be fired until then.
 *
 * Using the default constructor will cause the listener to be removed from the
 * component once the AncestorEvent is generated. A second constructor allows
 * you to specify a boolean value of false to prevent the AncestorListener from
 * being removed when the event is generated. This will allow you to reuse the
 * listener each time the event is generated.
 */
public class RequestFocusListener implements HierarchyListener {

    public RequestFocusListener() {
    }

    @Override
    public void hierarchyChanged(HierarchyEvent e) {
        final Component c = e.getComponent();
        if (c.isShowing() && (e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
            final Window toplevel = SwingUtilities.getWindowAncestor(c);
            toplevel.addWindowFocusListener(new WindowAdapter() {
                @Override
                public void windowGainedFocus(WindowEvent e) {
                    c.requestFocus();
                    toplevel.removeWindowFocusListener(this);
                }
            });
            c.removeHierarchyListener(this);
        }
    }
}
