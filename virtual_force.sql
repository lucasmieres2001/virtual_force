-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 25-12-2023 a las 22:32:08
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `virtual_force`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `admins`
--

CREATE TABLE `admins` (
  `id_admins` bigint(20) NOT NULL,
  `usuario` varchar(60) NOT NULL,
  `contrasenia` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Registro administradores';

--
-- Volcado de datos para la tabla `admins`
--

INSERT INTO `admins` (`id_admins`, `usuario`, `contrasenia`) VALUES
(1, 'Lucas_Dev', '$argon2id$v=19$m=1024,t=1,p=1$CcequX7ZYqV+vknu4YdmWQ$5TzxNI2IHziHY86ROWErRVdsSqOOHeKo9oCmQQ6MMds');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `atendido`
--

CREATE TABLE `atendido` (
  `id_atendido` bigint(20) NOT NULL,
  `id_encargo` bigint(20) DEFAULT NULL,
  `id_usuario` bigint(20) DEFAULT NULL,
  `fecha_encargo` varchar(15) DEFAULT NULL,
  `nombre` varchar(60) DEFAULT NULL,
  `apellido` varchar(60) DEFAULT NULL,
  `Telefono` varchar(25) DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `contrasenia` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `encargos`
--

CREATE TABLE `encargos` (
  `id_encargo` bigint(11) NOT NULL,
  `id_atendido` bigint(20) DEFAULT NULL,
  `encargo` varchar(100) DEFAULT NULL,
  `fecha_encargo` varchar(12) DEFAULT NULL,
  `pago` int(10) DEFAULT NULL,
  `comentario` varchar(3000) DEFAULT NULL,
  `respuesta` varchar(2500) DEFAULT NULL,
  `img` varchar(300) DEFAULT NULL,
  `nombre_imagen` varchar(50) DEFAULT NULL,
  `tipo_imagen` varchar(10) DEFAULT NULL,
  `enviar_img` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Registro de encargos';

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id_usuario` bigint(20) NOT NULL,
  `id_encargo` bigint(20) DEFAULT NULL,
  `nombre` varchar(60) DEFAULT NULL,
  `apellido` varchar(60) DEFAULT NULL,
  `telefono` varchar(25) DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `contrasenia` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Registro de usuarios';

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usuario`, `id_encargo`, `nombre`, `apellido`, `telefono`, `email`, `contrasenia`) VALUES
(32, NULL, 'lucas', 'mieres', '+543454126943', 'fueradelugar51@gmail.com', '$argon2id$v=19$m=1024,t=1,p=1$zV5LrqroWyJ3TvcxIcTcFA$M+2bTm3LEJjqulW7d828vL6IIt+Qy2xur2LtlOHD5Oc');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `admins`
--
ALTER TABLE `admins`
  ADD PRIMARY KEY (`id_admins`),
  ADD UNIQUE KEY `usuario` (`usuario`);

--
-- Indices de la tabla `atendido`
--
ALTER TABLE `atendido`
  ADD PRIMARY KEY (`id_atendido`),
  ADD KEY `id_encargos` (`id_encargo`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- Indices de la tabla `encargos`
--
ALTER TABLE `encargos`
  ADD PRIMARY KEY (`id_encargo`),
  ADD KEY `encargos_ibfk_1` (`id_atendido`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id_usuario`),
  ADD KEY `id_encargo` (`id_encargo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `admins`
--
ALTER TABLE `admins`
  MODIFY `id_admins` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `atendido`
--
ALTER TABLE `atendido`
  MODIFY `id_atendido` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT de la tabla `encargos`
--
ALTER TABLE `encargos`
  MODIFY `id_encargo` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id_usuario` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `atendido`
--
ALTER TABLE `atendido`
  ADD CONSTRAINT `atendido_ibfk_1` FOREIGN KEY (`id_encargo`) REFERENCES `encargos` (`id_encargo`) ON UPDATE CASCADE,
  ADD CONSTRAINT `atendido_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `encargos`
--
ALTER TABLE `encargos`
  ADD CONSTRAINT `encargos_ibfk_1` FOREIGN KEY (`id_atendido`) REFERENCES `atendido` (`id_atendido`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `usuarios_ibfk_1` FOREIGN KEY (`id_encargo`) REFERENCES `encargos` (`id_encargo`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
