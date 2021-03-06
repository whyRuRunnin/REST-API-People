package com.example.ase;

import com.example.ase.person.Person;
import com.example.ase.repository.PersonsRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AseApplicationTests {
	@Autowired
	PersonsRepository personsRepository;

	@Test
	@Order(1)
	public void testCreate () {
		Person p = new Person();
		// Id is autogenerated so keep that in mind when testing. "107" is just an example and won't work everytime.
		p.setId(107);
		p.setName("Robert");
		p.setLastname("Popescu");
		p.setZipcode(86157);
		p.setCity("Augsburg");
		p.setColor("rot");
		personsRepository.save(p);
		assertNotNull(personsRepository.findById(107).get());
	}

	@Test
	@Order(2)
	public void testReadAll () {
		List<Person> list = personsRepository.findAll();
		assertThat(list).size().isGreaterThan(0);
	}

	@Test
	@Order(3)
	public void testOnePerson() {
		Person p = personsRepository.findById(107).get();
		assertEquals(86157, p.getZipcode());
	}

	@Test
	@Order(4)
	public void testCommonColor() {
		List<Person> list = personsRepository.findAllByColor("rot");
		assertThat(list).size().isGreaterThan(0);
	}

	@Test
	@Order(5)
	public void testUpdate() {
		Person p = personsRepository.findById(107).get();
		p.setColor("blau");
		personsRepository.save(p);
		assertNotEquals("rot", personsRepository.findById(107).get().getColor());
	}

	@Test
	@Order(6)
	public void testDelete() {
		personsRepository.deleteById(107);
		assertThat(personsRepository.existsById(1)).isFalse();
	}
}
