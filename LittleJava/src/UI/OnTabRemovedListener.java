package UI;

import java.awt.event.ActionEvent;
import java.util.EventListener;

public interface OnTabRemovedListener extends EventListener{
	boolean onTabRemoved(ActionEvent e);
}
