package com.societegenerale.commons.plugin.rules;

import static com.societegenerale.commons.plugin.rules.NoLombokRuleTest.NO_LOMBOK_VIOLATION_MESSAGE;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Test;

import com.societegenerale.aut.main.ObjectWithLombokAnnotation;
import com.societegenerale.aut.main.ObjectWithNoLombokAnnotation;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;;

public class NoLombokRuleTestTest {

	@Test
	public void shouldThrowViolations() {

		assertExceptionIsThrownFor(ObjectWithLombokAnnotation.class);

	}

	@Test
	public void shouldNotThrowAnyViolation() {

		assertNoExceptionIsThrownFor(ObjectWithNoLombokAnnotation.class);

	}

	private void assertExceptionIsThrownFor(Class clazz) {

		JavaClasses classToTest = new ClassFileImporter().importClasses(clazz);

		assertThatThrownBy(() -> {
			classes().should(NoLombokRuleTest.notUseLombokLibraryAtClassLevel()).check(classToTest);
		}).hasMessageContaining(ObjectWithLombokAnnotation.class.getName())
				.hasMessageContaining(NO_LOMBOK_VIOLATION_MESSAGE);

	}

	private void assertNoExceptionIsThrownFor(Class clazz) {

		JavaClasses classToTest = new ClassFileImporter().importClasses(clazz);

		assertThatCode(() -> classes().should(NoLombokRuleTest.notUseLombokLibraryAtClassLevel()).check(classToTest))
				.doesNotThrowAnyException();

	}

}
