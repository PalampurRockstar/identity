package com.model.table;

import com.config.UserIdGenerator;
import com.fasterxml.jackson.annotation.*;
import com.model.category.UserType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static com.common.Constants.APP_ZONE_OFFSET;
import static com.common.util.DateTime.appDateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "app_user",uniqueConstraints = {
		@UniqueConstraint(columnNames = "username")
})
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class User {
	@Id
	@GeneratedValue(generator = UserIdGenerator.GENERATOR_NAME)
	@GenericGenerator(
			name = UserIdGenerator.GENERATOR_NAME,
			strategy = "com.config.UserIdGenerator",
			parameters = {@Parameter(name = UserIdGenerator.PREFIX_PARAM, value = "U")})
	private String id;
	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("last_name")
	private String lastName;

	@JsonProperty("date_of_birth")
	private String dateOfBirth;

	@JsonProperty("username")
	private String username;

	@JsonProperty("password")
	private String password;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("updated_at")
	private String updatedAt;

	@JsonProperty("type")
	private UserType type;

	@PrePersist
	protected void onCreate() {
		String nowDateTime = OffsetDateTime.now(ZoneOffset.ofHours(APP_ZONE_OFFSET)).format(appDateTimeFormat());
		createdAt = nowDateTime;
		updatedAt = nowDateTime;
	}
	@PreUpdate
	protected void onUpdate() {
		updatedAt = OffsetDateTime.now(ZoneOffset.ofHours(APP_ZONE_OFFSET)).format(appDateTimeFormat());
	}


}
