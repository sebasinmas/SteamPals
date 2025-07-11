import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Gamepad2, ArrowLeft } from "lucide-react";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e: React.FormEvent) => {
  e.preventDefault();

  try {
    const res = await fetch("http://localhost:8080/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        email: username,
        contrasenia: password,
      }),
    });

    if (!res.ok) {
      const errorMsg = await res.text();
      throw new Error(errorMsg || "Error desconocido");
    }

    const token = await res.text();
    localStorage.setItem("token", token);

    navigate("/home");
  } catch (err: any) {
    alert("Error al hacer login: " + err.message);
  }
};


  return (
    <div className="min-h-screen bg-gradient-to-br from-background via-background to-card">
      {/* Header */}
      <header className="border-b border-border/50 backdrop-blur-sm bg-background/80 sticky top-0 z-50">
        <div className="container mx-auto px-4 py-4">
          <div className="flex items-center justify-between">
            <Link to="/" className="flex items-center space-x-3">
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
            <Link to="/">
              <Button variant="ghost" className="hover:bg-muted/50">
                <ArrowLeft className="w-4 h-4 mr-2" />
                Volver
              </Button>
            </Link>
          </div>
        </div>
      </header>

      {/* Main Content */}
      <main className="container mx-auto px-4 py-8 lg:py-16">
        <div className="flex justify-center items-center min-h-[calc(100vh-200px)]">
          <Card className="w-full max-w-md bg-card/80 backdrop-blur-sm border-border/50 shadow-2xl">
            <CardHeader className="text-center space-y-2">
              <CardTitle className="text-3xl font-bold">
                Iniciar Sesión
              </CardTitle>
              <CardDescription className="text-lg">
                Accede a tu cuenta de SteamPals
              </CardDescription>
            </CardHeader>

            <CardContent>
              <form onSubmit={handleLogin} className="space-y-6">
                <div className="space-y-2">
                  <Label htmlFor="login-username" className="text-sm font-medium">
                    Nombre de Usuario
                  </Label>
                  <Input
                    id="login-username"
                    type="text"
                    placeholder="Tu nombre de usuario"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    className="h-12 bg-input/50 border-border/50 focus:border-primary transition-colors"
                    required
                  />
                </div>

                <div className="space-y-2">
                  <Label htmlFor="login-password" className="text-sm font-medium">
                    Contraseña
                  </Label>
                  <Input
                    id="login-password"
                    type="password"
                    placeholder="Tu contraseña"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="h-12 bg-input/50 border-border/50 focus:border-primary transition-colors"
                    required
                  />
                </div>

                <Button
                  type="submit"
                  className="w-full h-12 bg-gradient-to-r from-primary to-accent hover:from-primary/90 hover:to-accent/90 text-white font-semibold text-lg shadow-lg hover:shadow-xl transition-all duration-200"
                >
                  Iniciar Sesión
                </Button>
              </form>

              <div className="mt-6 text-center text-sm text-muted-foreground">
                ¿No tienes cuenta?{" "}
                <Link
                  to="/"
                  className="text-primary hover:text-accent font-medium hover:underline transition-colors"
                >
                  Regístrate aquí
                </Link>
              </div>
            </CardContent>
          </Card>
        </div>
      </main>

      {/* Background Effects */}
      <div className="fixed inset-0 -z-10 overflow-hidden pointer-events-none">
        <div className="absolute top-1/4 left-1/4 w-96 h-96 bg-primary/5 rounded-full blur-3xl animate-pulse"></div>
        <div className="absolute bottom-1/4 right-1/4 w-96 h-96 bg-accent/5 rounded-full blur-3xl animate-pulse delay-1000"></div>
        <div className="absolute top-3/4 left-3/4 w-64 h-64 bg-primary/3 rounded-full blur-2xl animate-pulse delay-2000"></div>
      </div>
    </div>
  );
}
