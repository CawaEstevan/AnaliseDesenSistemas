import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import java.util.List;

@Controller
public class WebController {
    private DatabaseProxy databaseProxy = new DatabaseProxy();
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @PostMapping("/inserir")
    @ResponseBody
    public String inserirUsuario(@RequestParam String nome, 
                               @RequestParam String email, 
                               @RequestParam int idade) {
        try {
            User user = new User(nome, email, idade);
            boolean success = databaseProxy.inserirUsuario(user);
            return success ? "Usuário inserido com sucesso! ID: " + user.getId() 
                          : "Falha ao inserir usuário";
        } catch (Exception e) {
            return "ERRO: " + e.getMessage();
        }
    }
    
    @PostMapping("/atualizar")
    @ResponseBody
    public String atualizarUsuario(@RequestParam Long id,
                                 @RequestParam String nome, 
                                 @RequestParam String email, 
                                 @RequestParam int idade) {
        try {
            User user = new User(nome, email, idade);
            user.setId(id);
            boolean success = databaseProxy.atualizarUsuario(user);
            return success ? "Usuário atualizado com sucesso!" 
                          : "Falha ao atualizar usuário";
        } catch (Exception e) {
            return "ERRO: " + e.getMessage();
        }
    }
    
    @PostMapping("/deletar")
    @ResponseBody
    public String deletarUsuario(@RequestParam Long id) {
        try {
            boolean success = databaseProxy.deletarUsuario(id);
            return success ? "Usuário deletado com sucesso!" 
                          : "Usuário não encontrado";
        } catch (Exception e) {
            return "ERRO: " + e.getMessage();
        }
    }
    
    @GetMapping("/buscar/{id}")
    @ResponseBody
    public String buscarUsuario(@PathVariable Long id) {
        try {
            User user = databaseProxy.buscarUsuario(id);
            return user != null ? user.toString() : "Usuário não encontrado";
        } catch (Exception e) {
            return "ERRO: " + e.getMessage();
        }
    }
    
    @GetMapping("/logs")
    @ResponseBody
    public List<String> getLogs() {
        return databaseProxy.getOperationLog();
    }
    
    @PostMapping("/clear-logs")
    @ResponseBody
    public String clearLogs() {
        databaseProxy.clearLog();
        return "Logs limpos com sucesso!";
    }
}