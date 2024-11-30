package com.gym.gym.service;

import static com.gym.gym.domain.enums.Status.BA;
import static com.gym.gym.domain.enums.Status.HA;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.gym.gym.domain.PaginatedObject;
import com.gym.gym.domain.ScheduledClasz;
import com.gym.gym.domain.enums.Role;
import com.gym.gym.repositories.clasz.ClaszEntity;
import com.gym.gym.repositories.customer.CustomerEntity;
import com.gym.gym.repositories.scheduledclasz.ScheduledClaszEntity;
import com.gym.gym.repositories.scheduledclasz.ScheduledClaszRepository;
import com.gym.gym.repositories.scheduledclaszinscription.ScheduledClaszInscriptionEntity;
import com.gym.gym.repositories.scheduledclaszinscription.ScheduledClaszInscriptionRepository;
import com.gym.gym.repositories.user.UserEntity;
import com.gym.gym.service.mapper.ScheduledClaszMapper;
import com.gym.gym.util.EmailUtils;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScheduledClaszService {

	private final ScheduledClaszRepository scheduledClaszRepository;

	private final ScheduledClaszMapper scheduledClaszMapper;

	private final ClaszService claszesService;

	private final EmailUtils emailUtils;

	private final ScheduledClaszInscriptionRepository scheduledClaszInscriptionRepository;

	@Scheduled(cron = "0 0 * * * ?")
	@Transactional
	public void generarScheduledClasz() {
		List<ClaszEntity> claszes = claszesService.findAllAvailableClaszes();

		LocalDate today = LocalDate.now();
		DayOfWeek actualDay = today.getDayOfWeek();

		List<ScheduledClaszEntity> scheduledClaszesToCreate = claszes.stream().map(clasz -> {
			Integer amountDaysToNextClass = clasz.getDay().getNumber() - actualDay.getValue();

			LocalDate nextClaszDay = today
					.plusDays(amountDaysToNextClass > 0 ? amountDaysToNextClass : amountDaysToNextClass + 7);

			LocalDateTime nextClaszDayInitTime = LocalDateTime.of(nextClaszDay, clasz.getInitSchedule().getTime());
			LocalDateTime nextClaszDayEndTime = LocalDateTime.of(nextClaszDay, clasz.getEndSchedule().getTime());

			boolean existClassSameLocalDateTime = scheduledClaszRepository
					.findByClaszAndInitDateTimeAndEndDateTimeAndTeacher(clasz, nextClaszDayInitTime,
							nextClaszDayEndTime, clasz.getTeacher())
					.isPresent();

			return (existClassSameLocalDateTime) ? null
					: new ScheduledClaszEntity(nextClaszDayInitTime, nextClaszDayEndTime, 0,
							clasz.getClassPersonsAmount(), clasz, clasz.getTeacher(), HA);
		}).filter(Objects::nonNull).toList();
		if (!scheduledClaszesToCreate.isEmpty()) {
			scheduledClaszRepository.saveAll(scheduledClaszesToCreate);
		}
	}

	public PaginatedObject<ScheduledClasz> findAllScheduledClasz(Long userId, String role, Integer pageNumber,
			Integer pageSize, String sortColumn, String sortDirection) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize,
				Sort.by(Direction.fromString(sortDirection), resolveSortProperty(sortColumn)));

		if (List.of(Role.ADMIN.name(), Role.OWNER.name()).contains(role)) {
			return buildResponse(scheduledClaszRepository.findAll(pageable));
		} else {
			LocalDateTime now = LocalDateTime.now().withSecond(0).withNano(0);
			if (Role.TEACHER.name().equals(role)) {
				return buildResponse(scheduledClaszRepository.findNonExpiredByTeacherUserId(userId, now, pageable));
			} else {
				return buildResponse(scheduledClaszRepository.findNonExpired(now, pageable));
			}
		}

	}

	private PaginatedObject<ScheduledClasz> buildResponse(Page<ScheduledClaszEntity> paginatedScheduledClaszes) {
		return new PaginatedObject<ScheduledClasz>(
				scheduledClaszMapper.entityListToDomainList(paginatedScheduledClaszes.getContent()),
				paginatedScheduledClaszes.getPageable().getPageNumber(),
				paginatedScheduledClaszes.getNumberOfElements(), paginatedScheduledClaszes.getTotalPages(),
				paginatedScheduledClaszes.getTotalElements());
	}

	private String resolveSortProperty(String property) {
		Map<String, String> sortProperties = new HashMap<String, String>();
		sortProperties.put("name", "clasz.name");
		sortProperties.put("initDateTime", "initDateTime");
		sortProperties.put("endDateTime", "endDateTime");
		sortProperties.put("actualClassPersonsAmount", "actualClassPersonsAmount");
		sortProperties.put("teacher", "teacher.user.person.lastName");

		return sortProperties.get(property);
	}

	public ScheduledClaszEntity findById(Long scheduledClaszId) {
		return scheduledClaszRepository.findById(scheduledClaszId).orElseThrow(() -> new EntityNotFoundException(
				String.format("No se encontró la clase programada con id %s", scheduledClaszId)));
	}

	@Transactional
	public void saveScheduledClasz(ScheduledClaszEntity scheduledClaszEntity) {
		scheduledClaszRepository.saveAndFlush(scheduledClaszEntity);
	}

	@Transactional
	public void saveScheduledClaszes(List<ScheduledClaszEntity> scheduledClaszesEntity) {
		scheduledClaszRepository.saveAllAndFlush(scheduledClaszesEntity);
	}

	public List<ScheduledClaszEntity> findNonExpiredByTeacherUserId(Long userId) {
		return scheduledClaszRepository.findNonExpiredByTeacherUserId(userId, LocalDateTime.now());
	}

	@Scheduled(cron = "0 * * * * *")
	@Transactional
	public void setStatusBAToExpiredScheduledClaszes() {
		List<ScheduledClaszEntity> expiredScheduledClaszes = scheduledClaszRepository.findexpired(LocalDateTime.now());

		expiredScheduledClaszes.forEach(elem -> elem.setStatus(BA));

		scheduledClaszRepository.saveAll(expiredScheduledClaszes);
	}

	@Transactional
	public void cancelScheduledClasz(Long scheduledClaszId) {
		ScheduledClaszEntity scheduledClaszEntity = findById(scheduledClaszId);
		List<ScheduledClaszInscriptionEntity> scheduledClaszList = scheduledClaszInscriptionRepository
				.findByScheduledClasz(scheduledClaszEntity);

		scheduledClaszEntity.setStatus(BA);
		scheduledClaszEntity.setActualClassPersonsAmount(0);
		saveScheduledClasz(scheduledClaszEntity);

		scheduledClaszInscriptionRepository.deleteAll(scheduledClaszList);
		scheduledClaszInscriptionRepository.flush();

		List<UserEntity> users = scheduledClaszList.stream().map(ScheduledClaszInscriptionEntity::getCustomer)
				.map(CustomerEntity::getUser).toList();

		users.forEach(user -> {
			Context context = new Context();
			context.setVariable("customerName", new StringBuilder(user.getPerson().getFirstName()).append(" ")
					.append(user.getPerson().getLastName()).toString());
			context.setVariable("className", scheduledClaszEntity.getClasz().getName());
			context.setVariable("classDay",
					scheduledClaszEntity.getInitDateTime().format(DateTimeFormatter.ISO_DATE).toString());

			emailUtils.sendEmail("facu.bonifacich@gmail.com",
					String.format("Cancelación de clase: %s", scheduledClaszEntity.getClasz().getName()), context,
					"cancel-class-template");
		});
	}
}
