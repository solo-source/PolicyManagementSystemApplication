package com.pms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.pms.entity.*;
import com.pms.service.PolicyClientService;

@Controller
@RequestMapping("/policy-management")
public class PolicyUIController {

    @Autowired
    private PolicyClientService policyClientService;
    
    // Dashboard page
    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard"; // returns dashboard.html
    }
    
    // ---------------------------
    // Create Policy
    // ---------------------------
    @GetMapping("/create-page")
    public String showCreatePolicyPage(Model model) {
        if (!model.containsAttribute("newPolicy")) {
            model.addAttribute("newPolicy", new Policy());
        }
        return "createPolicy"; // returns createPolicy.html
    }
    
    @PostMapping("/create")
    public String createPolicy(@ModelAttribute("newPolicy") Policy newPolicy,
                               RedirectAttributes redirectAttributes) {
        try {
            Policy createdPolicy = policyClientService.createPolicy(newPolicy);
            redirectAttributes.addFlashAttribute("message", 
                "Policy created successfully with ID: " + createdPolicy.getPolicyId());
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", 
                "Error creating policy: " + ex.getMessage());
        }
        return "redirect:/policy-management/dashboard";
    }
    
    // ---------------------------
    // Update Policy
    // ---------------------------
    @GetMapping("/update-page")
    public String showUpdatePolicyPage(Model model) {
        if (!model.containsAttribute("updatePolicy")) {
            model.addAttribute("updatePolicy", new Policy());
        }
        return "updatePolicy"; // returns updatePolicy.html
    }
    
    @PostMapping("/update")
    public String updatePolicy(@RequestParam("policyIdToUpdate") Long id,
                               @ModelAttribute("updatePolicy") Policy updatePolicy,
                               RedirectAttributes redirectAttributes) {
        try {
            Policy updated = policyClientService.updatePolicy(id, updatePolicy);
            redirectAttributes.addFlashAttribute("message", 
                "Policy updated successfully for ID: " + updated.getPolicyId());
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", 
                "Error updating policy: " + ex.getMessage());
        }
        return "redirect:/policy-management/dashboard";
    }
    
    // ---------------------------
    // Deactivate Policy
    // ---------------------------
    @GetMapping("/deactivate-page")
    public String showDeactivatePolicyPage() {
        return "deactivatePolicy"; // returns deactivatePolicy.html
    }
    
    @PostMapping("/deactivate")
    public String deactivatePolicy(@RequestParam("policyIdToDeactivate") Long id,
                                   RedirectAttributes redirectAttributes) {
        try {
            Policy deactivated = policyClientService.deactivatePolicy(id);
            redirectAttributes.addFlashAttribute("message", 
                "Policy deactivated successfully for ID: " + deactivated.getPolicyId());
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", 
                "Error deactivating policy: " + ex.getMessage());
        }
        return "redirect:/policy-management/dashboard";
    }
    
    // ---------------------------
    // VIEW OPERATIONS DASHBOARD
    // ---------------------------
    @GetMapping("/view-dashboard")
    public String showViewDashboard() {
        return "viewDashboard"; // returns viewDashboard.html
    }
    
    // View by ID
    @GetMapping("/view-by-id")
    public String showViewByIdPage() {
        return "viewPolicyById"; // returns viewPolicyById.html
    }
    
    @PostMapping("/view-by-id")
    public String viewById(@RequestParam("policyId") Long id,
                           RedirectAttributes redirectAttributes) {
        try {
            Policy policy = policyClientService.getPolicyById(id);
            redirectAttributes.addFlashAttribute("policies", new Policy[]{policy});
        } catch(Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/policy-management/view-dashboard";
    }
    
    // View by Scheme
    @GetMapping("/view-by-scheme")
    public String showViewBySchemePage() {
        return "viewPolicyByScheme"; // returns viewPolicyByScheme.html
    }
    
    @PostMapping("/view-by-scheme")
    public String viewByScheme(@RequestParam("schemeName") String schemeName,
                               RedirectAttributes redirectAttributes) {
        try {
            Policy[] policies = policyClientService.getPoliciesBySchemeName(schemeName);
            redirectAttributes.addFlashAttribute("policies", policies);
        } catch(Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/policy-management/view-dashboard";
    }
    
    // View by Date Purchased
    @GetMapping("/view-by-date")
    public String showViewByDatePage() {
        return "viewPolicyByDate"; // returns viewPolicyByDate.html
    }
    
    @PostMapping("/view-by-date")
    public String viewByDate(@RequestParam("date") String date,
                             RedirectAttributes redirectAttributes) {
        try {
            Policy[] policies = policyClientService.getPoliciesByDate(date);
            redirectAttributes.addFlashAttribute("policies", policies);
        } catch(Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/policy-management/view-dashboard";
    }
    
    // View by Premium Range
    @GetMapping("/view-by-premium")
    public String showViewByPremiumPage() {
        return "viewPolicyByPremium"; // returns viewPolicyByPremium.html
    }
    
    @PostMapping("/view-by-premium")
    public String viewByPremium(@RequestParam("minPremium") Double min,
                                @RequestParam("maxPremium") Double max,
                                RedirectAttributes redirectAttributes) {
        try {
            Policy[] policies = policyClientService.getPoliciesByPremiumRange(min, max);
            redirectAttributes.addFlashAttribute("policies", policies);
        } catch(Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/policy-management/view-dashboard";
    }
    
    // View by Maturity Range
    @GetMapping("/view-by-maturity")
    public String showViewByMaturityPage() {
        return "viewPolicyByMaturity"; // returns viewPolicyByMaturity.html
    }
    
    @PostMapping("/view-by-maturity")
    public String viewByMaturity(@RequestParam("minMaturity") Double min,
                                 @RequestParam("maxMaturity") Double max,
                                 RedirectAttributes redirectAttributes) {
        try {
            Policy[] policies = policyClientService.getPoliciesByMaturityRange(min, max);
            redirectAttributes.addFlashAttribute("policies", policies);
        } catch(Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/policy-management/view-dashboard";
    }
    
    // View by Number of Years
    @GetMapping("/view-by-years")
    public String showViewByYearsPage() {
        return "viewPolicyByYears"; // returns viewPolicyByYears.html
    }
    
    @PostMapping("/view-by-years")
    public String viewByYears(@RequestParam("years") Integer years,
                              RedirectAttributes redirectAttributes) {
        try {
            Policy[] policies = policyClientService.getPoliciesByYears(years);
            redirectAttributes.addFlashAttribute("policies", policies);
        } catch(Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/policy-management/view-dashboard";
    }
}
