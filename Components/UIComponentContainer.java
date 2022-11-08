package Components;

import java.util.List;

public abstract class UIComponentContainer implements UIComponent {

    protected List<UIComponent> components;

    public void setUIComponents(List<UIComponent> componentList) {
        for (UIComponent comp : componentList) {
            this.components.add(comp);
        }
    }

    public void addUIComponent(UIComponent comp) {
        this.components.add(comp);
    }

    // public List<UIComponent>

}