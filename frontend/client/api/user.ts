export async function updateUser(data: { email?: string; password?: string }) {
  const token = localStorage.getItem("token");
  if (!token) throw new Error("No hay token");

  const res = await fetch("http://localhost:8080/api/usuario", {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(data),
  });

  if (!res.ok) {
    const errorText = await res.text();
    throw new Error(errorText || "Error al actualizar usuario");
  }

  return await res.text(); // o return true
}
