package org.quiltmc.loader.impl.filesystem;

import java.io.IOException;
import java.nio.file.Path;

import org.quiltmc.loader.api.QuiltFileSystems.ExtendedFileSystemRef;
import org.quiltmc.loader.impl.filesystem.QuiltZipFileSystem.ZipHandling;
import org.quiltmc.loader.impl.util.QuiltLoaderInternal;
import org.quiltmc.loader.impl.util.QuiltLoaderInternalType;

@QuiltLoaderInternal(QuiltLoaderInternalType.NEW_INTERNAL)
public class QuiltFileSystemsImpl {

	public static ExtendedFileSystemRef createExtendedFileSystem(String name) {
		return new FsRef(new QuiltUnifiedFileSystem(name, true));
	}

	public static ExtendedFileSystemRef loadZipFile(String name, Path zipFile) throws IOException {
		return new FsRef(new QuiltZipFileSystem(name, zipFile, "", ZipHandling.PLAIN));
	}

	public static ExtendedFileSystemRef loadJarFile(String name, Path jarFile) throws IOException {
		return new FsRef(new QuiltZipFileSystem(name, jarFile, "", ZipHandling.JAR));
	}

	private static final class FsRef extends ExtendedFileSystemRef {

		FsRef(QuiltUnifiedFileSystem fs) {
			super(fs, fs.root);
		}

		FsRef(QuiltZipFileSystem fs) {
			super(fs, fs.root);
		}

		@Override
		public void markAsReadOnly() {
			((QuiltMapFileSystem<?, ?>) quiltFileSystem).switchToReadOnly();
		}
	}
}
