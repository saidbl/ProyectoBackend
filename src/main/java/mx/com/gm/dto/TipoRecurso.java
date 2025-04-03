
package mx.com.gm.dto;

public enum TipoRecurso {
    VIDEO("video", new String[]{"mp4", "mov", "avi"}),
    IMAGEN("image", new String[]{"jpg", "jpeg", "png"}),
    PDF("pdf", new String[]{"pdf"}),
    URL_EXTERNA("url", new String[]{});

    private final String nombreCarpeta;
    private final String[] extensionesPermitidas;

    TipoRecurso(String nombreCarpeta, String[] extensionesPermitidas) {
        this.nombreCarpeta = nombreCarpeta;
        this.extensionesPermitidas = extensionesPermitidas;
    }

    public String getNombreCarpeta() {
        return nombreCarpeta;
    }

    public String[] getExtensionesPermitidas() {
        return extensionesPermitidas;
    }

    public static TipoRecurso fromContentType(String contentType) {
        if (contentType == null) return null;
        
        if (contentType.startsWith("video/")) return VIDEO;
        if (contentType.startsWith("image/")) return IMAGEN;
        if (contentType.equals("application/pdf")) return PDF;
        
        return null;
    }
}
