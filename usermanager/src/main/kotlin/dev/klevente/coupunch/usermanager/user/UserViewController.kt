package dev.klevente.coupunch.usermanager.user

import dev.klevente.coupunch.usermanager.user.dto.NewUserRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.ModelAndView
import javax.validation.Valid

@Controller
class UserViewController(
    private val userService: UserService
) {

    @RequestMapping("/login")
    fun login() = "login"

    @RequestMapping("/register")
    fun register(request: WebRequest, model: Model): String {
        model.addAttribute("user", NewUserRequest())
        return "register"
    }

    @PostMapping("/register")
    fun register(
        @ModelAttribute("user") @Valid newUserRequest: NewUserRequest,
        result: BindingResult
    ): ModelAndView {
        if (result.hasErrors()) {
            return ModelAndView("register")
        }
        try {
            userService.register(newUserRequest)
        } catch (e: IllegalArgumentException) {
            return ModelAndView("register").apply {
                addObject("accountExists", "An account for that email already exists!")
            }
        }

        return ModelAndView("redirect:/login")
    }
}