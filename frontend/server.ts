import express from "express";

export function createServer() {
  const app = express();

  app.get("/api/hello", (_req, res) => {
    res.json({ message: "Hola desde Express" });
  });

  return app;
}
