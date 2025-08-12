package com.kurai.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.kurai.constant.ActiveStatus;
import com.kurai.constant.KycStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "clients")
@EntityListeners(AuditingEntityListener.class)
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String clientKey;

	@NotBlank
	private String name;

	@Email
	@NotBlank
	@Column(unique = true)
	private String email;

	@NotBlank
	@Pattern(regexp = "^[0-9]{10}$", message = "Invalid phone number")
	@Column(unique = true)
	private String phone;

	@NotBlank
	private String passwordHash;

	@Enumerated(EnumType.STRING)
	private ActiveStatus activeStatus;

	@Enumerated(EnumType.STRING)
	private KycStatus kycStatus;

	@CreatedDate
	@Column(name = "created_at", updatable = false)
	protected LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	protected LocalDateTime updatedAt;

	@CreatedBy
	@Column(name = "created_by", updatable = false)
	protected String createdBy;

	@LastModifiedBy
	@Column(name = "updated_by")
	protected String updatedBy;
}
