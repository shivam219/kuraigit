package com.kurai.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.kurai.constant.EmailStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "email_logs")
@EntityListeners(AuditingEntityListener.class)
public class EmailLogs {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "email_to")
	private String emailTo;

	@Column(name = "email_cc")
	private String emailCc;

	@Column(name = "email_bcc")
	private String emailBcc;

	private String subject;

	@Lob
	private String body;

	@Enumerated(EnumType.STRING)
	private EmailStatus status;

	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createdAt;

}
