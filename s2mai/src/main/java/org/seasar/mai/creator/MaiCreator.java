package org.seasar.mai.creator;

import org.seasar.framework.container.ComponentCustomizer;
import org.seasar.framework.container.creator.ComponentCreatorImpl;
import org.seasar.framework.container.deployer.InstanceDefFactory;
import org.seasar.framework.convention.NamingConvention;

public class MaiCreator extends ComponentCreatorImpl {

    public MaiCreator(NamingConvention namingConvention) {
        super(namingConvention);
        setNameSuffix("Mai");
        setInstanceDef(InstanceDefFactory.SINGLETON);
    }

    public ComponentCustomizer getMaiCustomizer() {
        return getCustomizer();
    }

    public void setMaiCustomizer(ComponentCustomizer customizer) {
        setCustomizer(customizer);
    }

}
