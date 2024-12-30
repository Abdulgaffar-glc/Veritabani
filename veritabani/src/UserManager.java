import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserManager {
    private String role;

    private static final Map<String, List<String>> rolePermissions = new HashMap<>();

    static {
        rolePermissions.put("stok", List.of("stok", "fire", "hammadde", "uretim", "iade"));
        rolePermissions.put("muhasebe", List.of("alim", "satis", "iade", "bayi", "firma", "fire"));
        rolePermissions.put("pazarlama", List.of("alim", "satis", "bayi", "firma", "hammadde", "stok"));
        rolePermissions.put("yonetici", List.of("alim", "satis", "bayi", "firma", "hammadde", "stok", "anakasa", "fire", "iade", "uretim"));
    }
    public boolean authenticateUser(String username, String password) {
        if ("muhasebe".equals(username) && "1234".equals(password)) {
            role = "muhasebe";
            return true;
        } else if ("pazarlama".equals(username) && "1234".equals(password)) {
            role = "pazarlama";
            return true;
        } else if ("stok".equals(username) && "1234".equals(password)) {
            role = "stok";
            return true;
        } else if ("yonetici".equals(username) && "1234".equals(password)) {
            role = "yonetici";
            return true;
        }
        return false;
    }

    public List<String> getPermissions() {
        return rolePermissions.getOrDefault(role, List.of());
    }

    public String getRole() {
        return role;
    }
}
