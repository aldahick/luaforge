package luaforge.core.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention( RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
/**
 * Annotate a method (returning Varargs and with a Varargs argument) with this.
 * @author samrg472
 */
public @interface LuaMethod {

    public String name() default "";
    
}
