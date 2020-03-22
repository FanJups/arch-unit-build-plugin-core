package com.societegenerale.commons.plugin.rules;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.societegenerale.commons.plugin.service.ScopePathProvider;
import com.societegenerale.commons.plugin.utils.ArchUtils;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * First of all, lombok is a great library because it helps developers to avoid
 * boilerplate code such as getters, setters, equals, hashCode, toString,...
 * 
 * However, it may not be the right solution when you are using JPA (Java
 * Persistence APi) or Hibernate or whatever tool you use to deal with Object
 * Relational Mapping.
 * 
 * Secondly, it hides the fact that POJO (Plain Old Java Object) might be too
 * large.
 * 
 * It's all about performance.
 * 
 * The goal of this rule is not to say that lombok is good or bad but to make
 * you think first before you use this library.
 * 
 * If lombok works for you, use it. If not, don't use it.
 * 
 * At the end of the day, what you really matters is not the tools but the fact
 * that you find the solution.
 * 
 * To make you better understand, these links will help you :
 * 
 * 
 * 
 * @see <a href= "https://twitter.com/simas_ch/status/1236565854734647299">Simon
 *      Martinelli's tweet</a> :
 * 
 *      <i>DON'T use Lombok's Data annotation with JPA! equals() and hashCode()
 *      will not be implemented the right way and toString() tirggers lazy
 *      loading </i>
 * 
 * @see <a href=
 *      "https://vladmihalcea.com/the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate/">Vlad
 *      Mihalcea's blog</a> :
 * 
 *      <i>the-best-way-to-implement-equals-hashcode-and-tostring-with-jpa-and-hibernate
 *      </i>
 * 
 * 
 * @see <a href= "https://twitter.com/struberg/status/786506216390270976">Mark
 *      Struberg's tweet</a> : <i>People, PLEASE do _not_ write toString()
 *      methods in #JPA entities! This will implicitly trigger lazy loading on
 *      all fields...</i>
 * 
 * 
 * @see <a href=
 *      "https://struberg.wordpress.com/2016/10/15/tostring-equals-and-hashcode-in-jpa-entities/">Mark
 *      Struberg's blog</a> :
 *      <i>tostring-equals-and-hashcode-in-jpa-entities</i>
 * 
 * 
 */

public class NoLombokRuleTest implements ArchRuleTest {

	protected static final String NO_LOMBOK_VIOLATION_MESSAGE = "You should not use lombok library at class level !";

	@Override
	public void execute(String path, ScopePathProvider scopePathProvider) {

		classes().should(notUseLombokLibraryAtClassLevel())
				.check(ArchUtils.importAllClassesInPackage(path, scopePathProvider.getMainClassesPath()));

	}

	protected static ArchCondition<JavaClass> notUseLombokLibraryAtClassLevel() {

		return new ArchCondition<JavaClass>(NO_LOMBOK_VIOLATION_MESSAGE) {

			@Override
			public void check(JavaClass javaClass, ConditionEvents events) {

				if (javaClass.isAnnotatedWith(Data.class) || javaClass.isAnnotatedWith(AllArgsConstructor.class)
						|| javaClass.isAnnotatedWith(NoArgsConstructor.class)
						|| javaClass.isAnnotatedWith(RequiredArgsConstructor.class)
						|| javaClass.isAnnotatedWith(EqualsAndHashCode.class)
						|| javaClass.isAnnotatedWith(ToString.class) || javaClass.isAnnotatedWith(Getter.class)
						|| javaClass.isAnnotatedWith(Setter.class)) {

					events.add(SimpleConditionEvent.violated(javaClass,
							NO_LOMBOK_VIOLATION_MESSAGE + " - class: " + javaClass.getName()));

				}

			}

		};
	}

}
