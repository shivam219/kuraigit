package com.kurai.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.kurai.constant.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@Entity
@Table(name = "payment_logs")
public class PaymentLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String razorpayOrderId;
	private String razorpayPaymentId;
	private String errorCode;
	@Column(columnDefinition = "TEXT")
	private String description;
	private String reason;
	private String source;
	private String step;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;

}
