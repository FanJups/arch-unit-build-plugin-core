package com.societegenerale.commons.plugin.rules;

import com.societegenerale.commons.plugin.SilentLog;
import com.societegenerale.commons.plugin.service.DefaultScopePathProvider;
import com.societegenerale.commons.plugin.utils.ArchUtils;
import org.junit.Before;
import org.junit.Test;

import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.catchThrowable;

public class DontReturnNullCollectionTestTest {

    String pathObjectWithAmethodReturningAnullList = "./target/aut-target/classes/com/societegenerale/aut/main/ObjectWithMethodsReturningNullCollections.class";

    String pathProperlyAnnotatedObjectWithAmethodReturningAlist = "./target/aut-target/classes/com/societegenerale/aut/main/ObjectWithProperlyAnnotatedMethodsReturningCollections.class";

    @Before
    public void setup(){
        //in the normal lifecycle, ArchUtils is instantiated, which enables a static field there to be initialized
        new ArchUtils(new SilentLog());
    }

    @Test
    public void shouldThrowViolations() {

        Throwable validationExceptionThrown = catchThrowable(() ->
            new DontReturnNullCollectionTest().execute(pathObjectWithAmethodReturningAnullList, new DefaultScopePathProvider(), emptySet())
        );

        assertThat(validationExceptionThrown).isInstanceOf(AssertionError.class)
                .hasMessageContaining("was violated (2 times)");

    }

    @Test
    public void shouldNotThrowViolations() {

        assertThatCode(() -> new DontReturnNullCollectionTest().execute(pathProperlyAnnotatedObjectWithAmethodReturningAlist, new DefaultScopePathProvider(),emptySet()))
                .doesNotThrowAnyException();

    }
}
