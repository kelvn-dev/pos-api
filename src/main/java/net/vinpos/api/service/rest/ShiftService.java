package net.vinpos.api.service.rest;

import net.vinpos.api.dto.rest.response.ShiftResDto;
import net.vinpos.api.enums.OrderStatus;
import net.vinpos.api.mapping.rest.ShiftMapper;
import net.vinpos.api.model.Order;
import net.vinpos.api.model.Shift;
import net.vinpos.api.model.User;
import net.vinpos.api.repository.ShiftRepository;
import net.vinpos.api.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShiftService extends BaseService<Shift, ShiftRepository> {
    private final ShiftMapper shiftMapper;
    private final UserService userService;
    private final OrderService orderService;
    private final ShiftRepository shiftRepository;

    public ShiftService(ShiftRepository repository, ShiftMapper shiftMapper, UserService userService, OrderService orderService, ShiftRepository shiftRepository) {
        super(repository);
        this.userService = userService;
        this.shiftMapper = shiftMapper;
        this.orderService = orderService;
        this.shiftRepository = shiftRepository;
    }

    @Transactional
    public ShiftResDto startShift() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User manager =
                (authentication instanceof JwtAuthenticationToken token)
                        ? userService.getByToken(token, false)
                        : new User();

        if (manager.getId() == null) {
            throw new RuntimeException("User not found");
        }

        Shift existingShift = repository.findOpenShift(Instant.now()).orElse(null);
        if (existingShift != null) {
            throw new RuntimeException("Shift is already open");
        }

        Shift shift = new Shift();
        shift.setStartTime(Instant.now());
        shift.setOpen(true);
        shift.setManager(manager);

        return shiftMapper.toResDto(repository.save(shift));
    }

    @Transactional
    public ShiftResDto endShift(UUID shiftId) {
        Shift shift = repository.findById(shiftId)
                .orElseThrow(() -> new RuntimeException("Shift not found"));
        if (!shift.isOpen()) {
            throw new RuntimeException("Shift is already closed");
        }

        shift.setEndTime(Instant.now());
        shift.setOpen(false);
        return shiftMapper.toResDto(repository.save(shift));
    }
}
