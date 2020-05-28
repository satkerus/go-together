package org.go.together.logic.service;

import org.apache.commons.lang3.StringUtils;
import org.go.together.context.RepositoryContext;
import org.go.together.dto.IdDto;
import org.go.together.dto.ResponseDto;
import org.go.together.dto.SimpleDto;
import org.go.together.dto.filter.FilterDto;
import org.go.together.dto.filter.FormDto;
import org.go.together.dto.filter.PageDto;
import org.go.together.exceptions.IncorrectFindObject;
import org.go.together.test.dto.JoinTestDto;
import org.go.together.test.dto.ManyJoinDto;
import org.go.together.test.dto.TestDto;
import org.go.together.test.entities.TestEntity;
import org.go.together.test.mapper.JoinTestMapper;
import org.go.together.test.mapper.ManyJoinMapper;
import org.go.together.test.mapper.TestMapper;
import org.go.together.test.repository.TestRepository;
import org.go.together.test.service.TestService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.util.*;

import static org.go.together.logic.find.enums.FindSqlOperator.IN;
import static org.go.together.logic.find.enums.FindSqlOperator.LIKE;
import static org.go.together.test.TestUtils.createTestDto;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ContextConfiguration(classes = RepositoryContext.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CrudServiceTest {
    TestDto testDto;
    @Autowired
    private TestService testService;
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private JoinTestMapper joinTestMapper;
    @Autowired
    private ManyJoinMapper manyJoinMapper;
    @Autowired
    private TestMapper testMapper;

    @BeforeEach
    public void init() {
        UUID id = UUID.randomUUID();
        String name = "test name";
        long number = 1;
        Date date = new Date();
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(date);
        startCalendar.add(Calendar.MONTH, 1);
        Date startDate = startCalendar.getTime();
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(startDate);
        endCalendar.add(Calendar.MONTH, 1);
        Date endDate = endCalendar.getTime();
        long startNumber = 1;
        long endNumber = 3;
        double latitude = 18.313230192867607;
        double longitude = 74.39449363632201;
        SimpleDto simpleDto = new SimpleDto("simpleDto", "simpleDto");

        testDto = createTestDto(id, name, number, date, startDate, endDate,
                startNumber, endNumber, simpleDto, longitude, latitude);

        testDto.getManyJoinEntities().stream().map(manyJoinMapper::dtoToEntity).forEach(entityManager::merge);
        testDto.getJoinTestEntities().stream().map(joinTestMapper::dtoToEntity).forEach(entityManager::merge);
    }

    @AfterEach
    public void clean() {
        testDto = null;
        entityManager.clear();
        testService.setAnotherClient(null);
    }

    @Test
    void create() {
        IdDto idDto = testService.create(testDto);

        Optional<TestEntity> savedEntity = testRepository.findById(idDto.getId());

        assertTrue(savedEntity.isPresent());
        assertEquals(testMapper.entityToDto(savedEntity.get()), testDto);
    }

    @Test
    void update() {
        final String newName = "new test name";
        IdDto savedId = testService.create(testDto);
        Optional<TestEntity> savedEntity = testRepository.findById(savedId.getId());
        assertTrue(savedEntity.isPresent());
        testDto.setName(newName);
        IdDto updatedId = testService.update(testDto);
        Optional<TestEntity> updatedEntity = testRepository.findById(updatedId.getId());

        assertTrue(updatedEntity.isPresent());
        assertEquals(savedId, updatedId);
        assertEquals(newName, updatedEntity.get().getName());
    }

    @Test
    void read() {
        IdDto savedId = testService.create(testDto);

        TestDto readDto = testService.read(savedId.getId());

        assertEquals(testDto, readDto);
    }

    @Test
    void delete() {
        IdDto savedId = testService.create(testDto);

        testService.delete(savedId.getId());

        Optional<TestEntity> deletedEntity = testRepository.findById(savedId.getId());

        assertTrue(deletedEntity.isEmpty());
    }

    @Test
    void validate() {
        String validate = testService.validate(testDto);

        assertTrue(StringUtils.isBlank(validate));
    }

    @Test
    void find() {
        testService.create(testDto);

        FormDto formDto = new FormDto();
        formDto.setMainIdField("test");
        FilterDto filterDto = new FilterDto();
        filterDto.setFilterType(LIKE);
        filterDto.setValues(Collections.singleton(new SimpleDto("test", "test")));
        formDto.setFilters(Collections.singletonMap("name", filterDto));
        PageDto pageDto = new PageDto();
        pageDto.setPage(0);
        pageDto.setSize(3);
        pageDto.setSort(Collections.emptyList());
        pageDto.setTotalSize(0L);
        formDto.setPage(pageDto);
        ResponseDto<Object> objectResponseDto = testService.find(formDto);

        Object result = objectResponseDto.getResult().iterator().next();

        assertEquals(1, objectResponseDto.getResult().size());
        assertEquals(1, objectResponseDto.getPage().getTotalSize());
        assertTrue(result instanceof TestDto);
        assertEquals(testDto, result);
    }

    @Test
    void findWithOneField() {
        testService.create(testDto);

        FormDto formDto = new FormDto();
        formDto.setMainIdField("test.id");
        FilterDto filterDto = new FilterDto();
        filterDto.setFilterType(LIKE);
        filterDto.setValues(Collections.singleton(new SimpleDto("test", "test")));
        formDto.setFilters(Collections.singletonMap("name", filterDto));
        PageDto pageDto = new PageDto();
        pageDto.setPage(0);
        pageDto.setSize(3);
        pageDto.setSort(Collections.emptyList());
        pageDto.setTotalSize(0L);
        formDto.setPage(pageDto);
        ResponseDto<Object> objectResponseDto = testService.find(formDto);

        Object result = objectResponseDto.getResult().iterator().next();

        assertEquals(1, objectResponseDto.getResult().size());
        assertEquals(1, objectResponseDto.getPage().getTotalSize());
        assertTrue(result instanceof UUID);
        assertEquals(testDto.getId(), result);
    }

    @Test
    void findByManyToManyTable() {
        testService.create(testDto);

        FormDto formDto = new FormDto();
        formDto.setMainIdField("test");
        FilterDto filterDto = new FilterDto();
        filterDto.setFilterType(IN);
        Set<SimpleDto> simpleDtos = new HashSet<>();
        for (ManyJoinDto manyJoinDto : testDto.getManyJoinEntities()) {
            simpleDtos.add(new SimpleDto(manyJoinDto.getId().toString(), manyJoinDto.getId().toString()));
        }
        filterDto.setValues(simpleDtos);
        formDto.setFilters(Collections.singletonMap("manyJoinEntities.id", filterDto));
        PageDto pageDto = new PageDto();
        pageDto.setPage(0);
        pageDto.setSize(3);
        pageDto.setSort(Collections.emptyList());
        pageDto.setTotalSize(0L);
        formDto.setPage(pageDto);
        ResponseDto<Object> objectResponseDto = testService.find(formDto);

        Object result = objectResponseDto.getResult().iterator().next();

        assertEquals(1, objectResponseDto.getResult().size());
        assertEquals(1, objectResponseDto.getPage().getTotalSize());
        assertTrue(result instanceof TestDto);
        assertEquals(testDto, result);
    }

    @Test
    void findWithUndefinedField() {
        assertThrows(
                IncorrectFindObject.class,
                () -> {
                    testService.create(testDto);

                    FormDto formDto = new FormDto();
                    formDto.setMainIdField("test");
                    FilterDto filterDto = new FilterDto();
                    filterDto.setFilterType(IN);
                    Set<SimpleDto> simpleDtos = new HashSet<>();
                    for (ManyJoinDto manyJoinDto : testDto.getManyJoinEntities()) {
                        simpleDtos.add(new SimpleDto(manyJoinDto.getId().toString(), manyJoinDto.getId().toString()));
                    }
                    filterDto.setValues(simpleDtos);
                    formDto.setFilters(Collections.singletonMap("someUndefinedField", filterDto));
                    PageDto pageDto = new PageDto();
                    pageDto.setPage(0);
                    pageDto.setSize(3);
                    pageDto.setSort(Collections.emptyList());
                    pageDto.setTotalSize(0L);
                    formDto.setPage(pageDto);
                    testService.find(formDto);
                });
    }

    @Test
    void findFromRemoteServiceToElements() {
        testService.create(testDto);

        FormDto formDto = new FormDto();
        formDto.setMainIdField("test");
        FilterDto filterDto = new FilterDto();
        filterDto.setFilterType(IN);
        filterDto.setValues(Collections.singleton(new SimpleDto("filter", "filter")));
        Collection<Object> uuids = new HashSet<>();
        for (String uuid : testDto.getElements()) {
            uuids.add(UUID.fromString(uuid));
        }
        testService.setAnotherClient(uuids);
        formDto.setFilters(Collections.singletonMap("elements?element.id", filterDto));
        PageDto pageDto = new PageDto();
        pageDto.setPage(0);
        pageDto.setSize(3);
        pageDto.setSort(Collections.emptyList());
        pageDto.setTotalSize(0L);
        formDto.setPage(pageDto);
        ResponseDto<Object> objectResponseDto = testService.find(formDto);

        Object result = objectResponseDto.getResult().iterator().next();

        assertEquals(1, objectResponseDto.getResult().size());
        assertEquals(1, objectResponseDto.getPage().getTotalSize());
        assertTrue(result instanceof TestDto);
        assertEquals(testDto, result);
    }

    @Test
    void findFromRemoteServiceToJoinTable() {
        testService.create(testDto);

        FormDto formDto = new FormDto();
        formDto.setMainIdField("test");
        FilterDto filterDto = new FilterDto();
        filterDto.setFilterType(IN);
        filterDto.setValues(Collections.singleton(new SimpleDto("filter", "filter")));
        Collection<Object> uuids = new HashSet<>();
        for (JoinTestDto joinTestDto : testDto.getJoinTestEntities()) {
            uuids.add(joinTestDto.getId());
        }
        testService.setAnotherClient(uuids);
        formDto.setFilters(Collections.singletonMap("joinTestEntities.id?join.id", filterDto));
        PageDto pageDto = new PageDto();
        pageDto.setPage(0);
        pageDto.setSize(3);
        pageDto.setSort(Collections.emptyList());
        pageDto.setTotalSize(0L);
        formDto.setPage(pageDto);
        ResponseDto<Object> objectResponseDto = testService.find(formDto);

        Object result = objectResponseDto.getResult().iterator().next();

        assertEquals(1, objectResponseDto.getResult().size());
        assertEquals(1, objectResponseDto.getPage().getTotalSize());
        assertTrue(result instanceof TestDto);
        assertEquals(testDto, result);
    }

    @Test
    void findFromRemoteServiceEmptyResult() {
        testService.create(testDto);

        FormDto formDto = new FormDto();
        formDto.setMainIdField("test");
        FilterDto filterDto = new FilterDto();
        filterDto.setFilterType(IN);
        filterDto.setValues(Collections.singleton(new SimpleDto("filter", "filter")));
        testService.setAnotherClient(Collections.emptyList());
        formDto.setFilters(Collections.singletonMap("joinTestEntities.id?join.id", filterDto));
        PageDto pageDto = new PageDto();
        pageDto.setPage(0);
        pageDto.setSize(3);
        pageDto.setSort(Collections.emptyList());
        pageDto.setTotalSize(0L);
        formDto.setPage(pageDto);
        ResponseDto<Object> objectResponseDto = testService.find(formDto);

        assertEquals(0, objectResponseDto.getResult().size());
        assertEquals(0, objectResponseDto.getPage().getTotalSize());
    }
}