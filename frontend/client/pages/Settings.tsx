import { useState } from "react";
import { Link } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Gamepad2, ArrowLeft, Lock, Trash2, Monitor } from "lucide-react";
import { updateUser } from "@/api/user";
import { useEffect } from "react";


export default function Settings() {
  const [email, setEmail] = useState("");
  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [steamConnected, setSteamConnected] = useState(true);
  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const token = localStorage.getItem("token");
        if (!token) return;

        const response = await fetch("http://localhost:8080/api/usuario/me", {
          headers: {
            Authorization: `Bearer ${token}`,
          }
        });

        if (!response.ok) throw new Error("No se pudo obtener el usuario");

        const data = await response.json();
        setEmail(data.email);
      } catch (err) {
        console.error("Error obteniendo usuario:", err);
      }
    };

    fetchUserData();
  }, []);


  const handleUpdateEmail = async () => {
    try {
      await updateUser({ email });
      alert("Email actualizado correctamente");
    } catch (err) {
      alert("Error al actualizar email: " + (err as Error).message);
    }
  };

  const handleUpdatePassword = async () => {
    if (newPassword !== confirmPassword) {
      alert("Las contraseñas no coinciden");
      return;
    }
    try {
      await updateUser({ password: newPassword });
      alert("Contraseña actualizada correctamente");
      setCurrentPassword("");
      setNewPassword("");
      setConfirmPassword("");
    } catch (err) {
      alert("Error al actualizar contraseña: " + (err as Error).message);
    }
  };

  const handleSteamAction = () => {
  if (steamConnected) {
    // 🔴 Desconectar (llamar a tu backend para "unlinkear" Steam)
    fetch("http://localhost:8080/api/steam/unlink", {
      method: "POST",
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    })
      .then((res) => {
        if (res.ok) {
          setSteamConnected(false);
          console.log("Steam desconectado");
        } else {
          console.error("Error al desconectar Steam");
        }
      })
      .catch((err) => console.error(err));
  } else {
    // 🟢 Conectar: redirige al backend para iniciar login con Steam
    window.location.href = "http://localhost:8080/auth/steam/login";
  }
};



  const deleteAccount = () => {
    if (
      confirm(
        "¿Estás seguro de que quieres eliminar tu cuenta? Esta acción no se puede deshacer.",
      )
    ) {
      console.log("Eliminando cuenta...");
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-background via-background to-card">
      <header className="border-b border-border/50 backdrop-blur-sm bg-background/80 sticky top-0 z-50">
        <div className="container mx-auto px-4 py-4">
          <div className="flex items-center justify-between">
            <Link to="/home" className="flex items-center space-x-3">
              <div className="relative">
                <div className="w-10 h-10 rounded-full bg-gradient-to-br from-primary to-accent flex items-center justify-center">
                  <Gamepad2 className="w-6 h-6 text-primary-foreground" />
                </div>
                <div className="absolute -top-1 -right-1 w-4 h-4 bg-accent rounded-full animate-pulse"></div>
              </div>
              <h1 className="text-2xl font-bold bg-gradient-to-r from-primary to-accent bg-clip-text text-transparent">
                SteamPals
              </h1>
            </Link>
            <Link to="/home">
              <Button variant="ghost" className="hover:bg-muted/50">
                <ArrowLeft className="w-4 h-4 mr-2" />
                Volver
              </Button>
            </Link>
          </div>
        </div>
      </header>

      <main className="container mx-auto px-4 py-8 max-w-2xl">
        <div className="space-y-8">
          <div>
            <h1 className="text-3xl font-bold mb-2">Configuración</h1>
            <p className="text-muted-foreground">
              Gestiona tu cuenta y configuración
            </p>
          </div>

          <div className="space-y-6">
            {/* Email */}
            <Card className="bg-card/80 backdrop-blur-sm border-border/50">
              <CardHeader>
                <CardTitle className="flex items-center">
                  <Lock className="w-5 h-5 mr-2" />
                  Email
                </CardTitle>
              </CardHeader>
              <CardContent className="space-y-4">
                <div className="space-y-2">
                  <Label htmlFor="email">Dirección de correo</Label>
                  <Input
                    id="email"
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                  />
                </div>
                <Button size="sm" onClick={handleUpdateEmail}>
                  Actualizar email
                </Button>
              </CardContent>
            </Card>

            {/* Password */}
            <Card className="bg-card/80 backdrop-blur-sm border-border/50">
              <CardHeader>
                <CardTitle>Cambiar contraseña</CardTitle>
              </CardHeader>
              <CardContent className="space-y-4">
                <div className="space-y-2">
                  <Label htmlFor="current-password">Contraseña actual</Label>
                  <Input
                    id="current-password"
                    type="password"
                    value={currentPassword}
                    onChange={(e) => setCurrentPassword(e.target.value)}
                    placeholder="Tu contraseña actual"
                  />
                </div>
                <div className="space-y-2">
                  <Label htmlFor="new-password">Nueva contraseña</Label>
                  <Input
                    id="new-password"
                    type="password"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                    placeholder="Nueva contraseña"
                  />
                </div>
                <div className="space-y-2">
                  <Label htmlFor="confirm-password">
                    Confirmar nueva contraseña
                  </Label>
                  <Input
                    id="confirm-password"
                    type="password"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                    placeholder="Confirmar nueva contraseña"
                  />
                </div>
                <Button onClick={handleUpdatePassword} size="sm">
                  Actualizar contraseña
                </Button>
              </CardContent>
            </Card>

            {/* Steam Connection */}
            <Card className="bg-card/80 backdrop-blur-sm border-border/50">
              <CardHeader>
                <CardTitle className="flex items-center">
                  <Monitor className="w-5 h-5 mr-2" />
                  Conexión Steam
                </CardTitle>
              </CardHeader>
              <CardContent>
                <div className="flex items-center justify-between p-4 bg-muted/20 rounded-lg">
                  <div className="flex items-center space-x-3">
                    <Monitor className="w-6 h-6 text-primary" />
                    <div>
                      <div className="font-medium">Steam</div>
                      <div className="text-sm text-muted-foreground">
                        {steamConnected ? "Conectado" : "Desconectado"}
                      </div>
                    </div>
                  </div>
                  <Button
                    variant={steamConnected ? "destructive" : "default"}
                    onClick={handleSteamAction}
                    size="sm"
                  >
                    {steamConnected ? "Desconectar" : "Conectar"}
                  </Button>
                </div>
                {!steamConnected && (
                  <p className="text-sm text-muted-foreground mt-3">
                    Conecta tu cuenta de Steam para sincronizar tus juegos y
                    estadísticas.
                  </p>
                )}
              </CardContent>
            </Card>

            {/* Delete Account */}
            <Card className="bg-card/80 backdrop-blur-sm border-border/50 border-destructive/50">
              <CardHeader>
                <CardTitle className="text-destructive">
                  Eliminar cuenta
                </CardTitle>
              </CardHeader>
              <CardContent className="space-y-4">
                <p className="text-sm text-muted-foreground">
                  Esta acción eliminará permanentemente tu cuenta y todos tus
                  datos. No podrás recuperar tu información una vez eliminada.
                </p>
                <Button
                  variant="destructive"
                  onClick={deleteAccount}
                  className="w-full justify-start"
                >
                  <Trash2 className="w-4 h-4 mr-2" />
                  Eliminar mi cuenta
                </Button>
              </CardContent>
            </Card>
          </div>
        </div>
      </main>
    </div>
  );
}
