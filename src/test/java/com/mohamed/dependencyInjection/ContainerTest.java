package com.mohamed.dependencyInjection;

import static org.junit.Assert.*;

import com.mohamed.dependencyAbstractClasses.*;
import com.mohamed.dependencyClasses.A;
import com.mohamed.dependencyClasses.B;
import com.mohamed.dependencyClasses.C;
import com.mohamed.dependencyClasses.D;
import org.junit.*;


public class ContainerTest {

    Container myContainer;

    @Before
    public void setUp() {
        myContainer = new Container();
        myContainer.registerSimpleDependency(A.class);
        myContainer.registerSimpleDependency(B.class);
        myContainer.registerSimpleDependency(C.class);
        myContainer.registerSimpleDependency(D.class);
        myContainer.registerSimpleDependency(BundleOfAbstractDependencies.class);
        myContainer.registerAbstractDependency(AbstractClass.class, ImplementationOne.class);
        myContainer.registerAbstractDependency(MyInterface.class, ImplementationTwo.class);
        myContainer.registerNamedDependency("db_path","C:/test");
        myContainer.registerNamedDependency("number_of_servers",5);
        myContainer.registerNamedDependency("my_name","EL HENTOUR MOHAMED");
    }

    @Test
    public void testInstanciationOfA() throws Exception {
        A a = myContainer.getInstance(A.class);
        assertNotNull(a);
    }

    @Test
    public void testInstanciationOfB() throws Exception {
        B b = myContainer.getInstance(B.class);
        assertNotNull(b);
        assertNotNull(b.getA());
    }

    @Test
    public void testInstanciationOfC() throws Exception {
        C c = myContainer.getInstance(C.class);
        assertNotNull(c);
        assertNotNull(c.getB());
        assertNotNull(c.getB().getA());
    }

    @Test
    public void testInstanciationOfD() throws Exception {
        D d = myContainer.getInstance(D.class);
        assertNotNull(d);
        assertNotNull(d.getB());
        assertNotNull(d.getB().getA());
        assertEquals("C:/test",d.getInjectedString());
        assertEquals(5,d.getNumberOfServers());
    }

    @Test
    public void testInstanciationOfImplementationOne() throws Exception {
            AbstractClass abstractClass = myContainer.getInstance(AbstractClass.class);
            assertNotNull(abstractClass);
            assertTrue(abstractClass instanceof ImplementationOne);
            assertNotNull(((ImplementationOne) abstractClass).getA());
            // The field A should be the same reference as the Field A inside D since it's been created once and returned each time
            assertTrue(((ImplementationOne) abstractClass).getA()==((ImplementationOne) abstractClass).getD().getB().getA());
    }

    @Test
    public void testInstanctiationOfImplementationTwo() throws Exception{
        MyInterface myInterface = myContainer.getInstance(MyInterface.class);
        assertNotNull(myInterface);
        assertTrue(myInterface instanceof ImplementationTwo);
        assertNotNull(((ImplementationTwo) myInterface).getA());

    }

    @Test
    public void testInstanctiationOfBundleOfAbstractDependencies() throws Exception{
        BundleOfAbstractDependencies bundleOfAbstractDependencies = myContainer.getInstance(BundleOfAbstractDependencies.class);
        assertNotNull(bundleOfAbstractDependencies);
        assertNotNull(bundleOfAbstractDependencies.getAbstractClass());
        assertNotNull(bundleOfAbstractDependencies.getMyInterface());
        assertTrue(bundleOfAbstractDependencies.getAbstractClass() instanceof ImplementationOne);
        assertTrue(bundleOfAbstractDependencies.getMyInterface() instanceof ImplementationTwo);
        assertEquals("EL HENTOUR MOHAMED",((ImplementationTwo) bundleOfAbstractDependencies.getMyInterface()).getMyName());
        // Test if the object A inside AbstractClass is the same reference as object A inside MyInterface
        assertTrue(((ImplementationOne) bundleOfAbstractDependencies.getAbstractClass()).getA()==((ImplementationTwo) bundleOfAbstractDependencies.getMyInterface()).getA());

    }

}