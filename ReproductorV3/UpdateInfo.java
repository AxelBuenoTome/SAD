package ReproductorV3;

public class UpdateInfo {

    private final int type; //Los tipos están declarados en KEY
    private final Object value;

    public UpdateInfo(int type, Object value) {
        this.type = type;
        this.value = value;
    }
    public int getType() {
        return type;
    }
    public Object getValue() {
        return value;
    }
}