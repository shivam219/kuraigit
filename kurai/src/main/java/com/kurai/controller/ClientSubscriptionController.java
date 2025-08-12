package com.kurai.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kurai.constant.PlanType;
import com.kurai.dto.ClientSubscriptionDTO;
import com.kurai.entity.ClientSubscription;
import com.kurai.entity.SubscriptionPlan;
import com.kurai.model.ClientPrincipal;
import com.kurai.repository.ClientSubscriptionRepository;
import com.kurai.repository.SubscriptionPlanRepository;
import com.kurai.security.LoggedInUser;
import com.kurai.service.ClientSubscriptionService;
import com.kurai.service.SubscriptionPlanTypeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class ClientSubscriptionController {

	@Autowired
	private SubscriptionPlanRepository planRepository;

	@Autowired
	private ClientSubscriptionRepository subscriptionRepository;

	@Autowired
	private ClientSubscriptionService clientSubscriptionService;

	@Autowired
	private SubscriptionPlanTypeService subscriptionPlanTypeService;

	/* Fetch all available subscription plans */
	@GetMapping("/plans")
	public List<SubscriptionPlan> getAvailablePlans(@LoggedInUser ClientPrincipal client) {
	    boolean hasFreePlan = subscriptionRepository.existsByClientIdAndSubscriptionPlan_PlanType(client.getClientId(), PlanType.FREE);
	    if (hasFreePlan) {
	        return planRepository.findByPlanTypeNot(PlanType.FREE); // exclude FREE
	    } else {
	        return planRepository.findAll(); // show all
	    }
	}

	/* End Fetch all available subscription plans */

	/* Fetch plan type *//*Writing the controller method to add, update plan type*/
/*	@GetMapping("/plan-types")
	public ResponseEntity<List<SubscriptionPlanType>> getPlanTypes() {
		return ResponseEntity.ok(subscriptionPlanTypeService.getAllTypes());
	}
*/
	@GetMapping("/plan-types")
	public ResponseEntity<List<Map<String, String>>> getPlanTypesEnum() {
	    List<Map<String, String>> types = Arrays.stream(PlanType.values())
	        .map(type -> {
	            Map<String, String> map = new HashMap<>();
	            map.put("value", type.name());
	            map.put("label", type == PlanType.FREE ? "🟢 Free" : "🟡 Paid");
	            return map;
	        })
	        .collect(Collectors.toList());

	    return ResponseEntity.ok(types);
	}

	/* End Fetch plan type */

	/* Fetch current active subscription of a client #Not Used*/
	@GetMapping("/client-current-activated-subscription")
	public ResponseEntity<?> getCurrentSubscription(@LoggedInUser ClientPrincipal client) {
		Optional<ClientSubscriptionDTO> clientSubscription = subscriptionRepository
				.findCurrentActivatedSubscriptionDTO(client.getClientId());
		if (clientSubscription.isEmpty()) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.ok(clientSubscription.get());
		}
	}
	/* End Fetch current active subscription of a client #Not Used */

	/* Fetch all subscription of a client */
	@GetMapping("/client-subscription-current")
	public ResponseEntity<?> getClientSubscription(@LoggedInUser ClientPrincipal client) {
		List<ClientSubscriptionDTO> clientSubscription = subscriptionRepository
				.findCurrentSubscriptionDTO(client.getClientId());
		return ResponseEntity.ok(clientSubscription);
	}
	/* End Fetch all active subscription of a client */

	/* activated subscription plan */
	@PostMapping("/client-subscription-active")
	public ResponseEntity<?> activateSubscription(@RequestParam Long clientPlanId,
			@LoggedInUser ClientPrincipal client) {
		ClientSubscription clientSubscription = clientSubscriptionService.activateSubscription(client.getClientId(),
				clientPlanId);
		if (clientSubscription == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to active the plan");
		}
		return ResponseEntity.ok().build();
	}
	/* End activated subscription plan */
	/* Turn on auto activate subscription plan */
	@PostMapping("/client-subscription-auto-active")
	public ResponseEntity<?> turnOnAutoActiveSubscription(@RequestParam Long clientPlanId,
			@LoggedInUser ClientPrincipal client) {
		ClientSubscription clientSubscription = clientSubscriptionService.turnOnAutoActiveSubscription(client.getClientId(),
				clientPlanId);
		if (clientSubscription == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to turn on auto active the plan");
		}
		return ResponseEntity.ok().build();
	}
	/* Turn on auto activate subscription plan */

	/* Create a new subscription plan */
	@PostMapping("/client-subscription-buy")
	public ResponseEntity<?> subscriptionBuy(@RequestParam Long planId, @LoggedInUser ClientPrincipal client) {
		ClientSubscription clientSubscription = clientSubscriptionService.buySubscription(client.getClientId(), planId);
		if (clientSubscription == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to buy the plan");
		}
		return ResponseEntity.ok().build();
	}
	/* End Create a new subscription plan */

	// Dummy "add to cart" functionality (for future checkout integration)
	@PostMapping("/cart/add")
	public ResponseEntity<String> addToCart(@RequestBody Long planId) {
		// You can log or store the selected plan temporarily
		return ResponseEntity.ok("Subscription plan " + planId + " added to cart (not persisted yet)");
	}
}
